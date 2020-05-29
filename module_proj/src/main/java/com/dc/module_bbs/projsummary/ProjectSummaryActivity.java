package com.dc.module_bbs.projsummary;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.common.CommonConstant;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.module_bbs.R;
import com.dc.module_bbs.searchlist.SearchActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.scwang.smartrefresh.layout.constant.RefreshState;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//项目详情 图表
@Route(path = ArounterManager.PROJECTSUMMARYACTIVITY_URL)
public class ProjectSummaryActivity extends AbsLifecycleActivity<ProjectSummaryViewModel> implements View.OnClickListener {

    private PieChart pieChart;
    private BarChart barChart, barChar_project_schedule;
    private String region;
    private int company_id;
    private TextView tv_project_cnt, tv_already_investment, tv_unalready_investment;
    private Button btn_projList;

    @Override
    protected int getLayout() {
        return R.layout.proj_activity_projectsummary;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        if (getIntent() != null) {
            region = getIntent().getStringExtra(CommonConstant.PRPJECT_ARTEA);
            company_id = UserManager.getInstance().getUserInfo(getApplicationContext()).company_id;
        }
        setTitle(region);
        btn_projList = findViewById(R.id.btn_projList);
        btn_projList.setOnClickListener(this);
        tv_already_investment = findViewById(R.id.tv_already_investment);
        tv_unalready_investment = findViewById(R.id.tv_unalready_investment);
        tv_project_cnt = findViewById(R.id.tv_project_cnt);
        pieChart = findViewById(R.id.piechart_investment_proportion);
        barChart = findViewById(R.id.proj_bar_chart);
        barChar_project_schedule = findViewById(R.id.barChar_project_schedule);
        initPieChart();
        initDoubleBarChart(barChart);
        initOnlyBarChart(barChar_project_schedule);
        mViewModel.toGetownerCompanyBoard(company_id, region);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerSubscriber(mViewModel.mRepository.EVENT_AREA_PROJECT, ProjectAreaItem.class).observe(this, new Observer<ProjectAreaItem>() {
            @Override
            public void onChanged(@Nullable ProjectAreaItem projectareaitem) {
                // todo 设置数据
                tofillBarData(projectareaitem, barChart);
                initPieChartData(projectareaitem);
                toFillBottonBarData(projectareaitem, barChar_project_schedule);

            }
        });
    }

    private void initPieChart() {
        Description description = pieChart.getDescription();
        description.setText(""); //设置描述的文字
        pieChart.setDrawEntryLabels(false);

        pieChart.setHighlightPerTapEnabled(true); //设置piecahrt图表点击Item高亮是否可用
        pieChart.animateX(2000);
        pieChart.setUsePercentValues(true);//设置使用百分比（后续有详细介绍）
        pieChart.setDrawEntryLabels(false); // 设置entry中的描述label是否画进饼状图中
        pieChart.setEntryLabelColor(Color.parseColor("#666666"));//设置该文字是的颜色
        pieChart.setEntryLabelTextSize(12f);//设置该文字的字体大小

        pieChart.setDrawHoleEnabled(true);//设置圆孔的显隐，也就是内圆
        pieChart.setHoleRadius(51f);//设置内圆的半径。外圆的半径好像是不能设置的，改变控件的宽度和高度，半径会自适应。
        pieChart.setHoleColor(Color.WHITE);//设置内圆的颜色
        pieChart.setDrawCenterText(true);//设置是否显示文字

        pieChart.setTransparentCircleRadius(25f);//设置内圆和外圆的一个交叉园的半径，这样会凸显内外部的空间
        pieChart.setTransparentCircleColor(Color.BLACK);//设置透明圆的颜色
        pieChart.setTransparentCircleAlpha(50);//设置透明圆你的透明度


        Legend legend = pieChart.getLegend();//图例
        legend.setEnabled(false);//是否显示
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//对齐
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//对齐
        legend.setForm(Legend.LegendForm.DEFAULT);//设置图例的图形样式,默认为圆形
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);//设置图例的排列走向:vertacal相当于分行
        legend.setFormSize(6f);//设置图例的大小
        legend.setTextSize(8f);//设置图注的字体大小
        legend.setFormToTextSpace(4f);//设置图例到图注的距离

        legend.setDrawInside(true);//不是很清楚
        legend.setWordWrapEnabled(false);//设置图列换行(注意使用影响性能,仅适用legend位于图表下面)，我也不知道怎么用的
        legend.setTextColor(Color.BLACK);


    }

/*    private SpannableString getCommentCountSpannableString(float investment) {
        if (investment < 0) {
            investment = 0;
        }
        String string = getContext().getResources().getString(R.string.reply_desc);
        int start = string.length();
        string = string + " ";
        int mid = string.length();
        string = string + "(" + commentCount + ")";
        int end = string.length();
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new TextAppearanceSpan(getContext(), R.style.style_black), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new TextAppearanceSpan(getContext(), R.style.style_gray), mid, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }*/

    private void initPieChartData(ProjectAreaItem projectareaitem) {
//        getCommentCountSpannableString(projectareaitem.investment);
        pieChart.setCenterText(projectareaitem.investment + "亿\n总投资额(亿)");//设置饼状图中心的文字
        pieChart.setCenterTextSize(16f);//设置文字的消息
        pieChart.setCenterTextColor(Color.parseColor("#333333"));//设置文字的颜色
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry((float) projectareaitem.noWorksPercentage));
        pieEntries.add(new PieEntry((float) projectareaitem.WorksPercentage));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, null);
        pieDataSet.setColors(Color.parseColor("#cfdef9"), Color.parseColor("#36b365"));
        pieDataSet.setSliceSpace(3f);//设置每块饼之间的空隙
        pieDataSet.setSelectionShift(10f);//点击某个饼时拉长的宽度

        PieData pieData = new PieData(pieDataSet);

//        DecimalFormat percentFormat = new DecimalFormat();
//        percentFormat.applyPattern("#0%");
//        PercentFormatter percentFormatter=new
        pieData.setValueFormatter(new PercentFormatter(new DecimalFormat("###,###,##0.00")));


        pieData.setDrawValues(true);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.parseColor("#333333"));
        pieChart.setCenterTextSize(16);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }


    private void initDoubleBarChart(BarChart doubleBarChar) {
        Description description = barChart.getDescription();
        description.setText("");
//        description.setYOffset();
//        description.setTextSize(10f);
        doubleBarChar.setNoDataText("暂无数据");
        // 集双指缩放
        doubleBarChar.setPinchZoom(false);
        doubleBarChar.animateY(2000);

//        tofillBarData(doubleBarChar);

        Legend legend = doubleBarChar.getLegend();//图例
        legend.setEnabled(true);//是否显示
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//对齐
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);//对齐
        legend.setForm(Legend.LegendForm.SQUARE);//设置图例的图形样式,默认为圆形
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//设置图例的排列走向:vertacal相当于分行
        legend.setFormSize(11f);//设置图例的大小
        legend.setTextSize(12f);//设置图注的字体大小
        legend.setTextColor(Color.parseColor("#333333"));
        legend.setFormToTextSpace(4f);//设置图例到图注的距离


        XAxis xAxis = doubleBarChar.getXAxis();
        xAxis.setDrawLabels(true);//是否显示x坐标的数据
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x坐标数据的位置
        xAxis.setDrawGridLines(false);//是否显示网格线中与x轴垂直的网格线
        xAxis.setLabelCount(2, true);//设置x轴显示的标签的个数
        final List<String> xValue = new ArrayList<>();
        xValue.add(getResources().getString(R.string.no_work));//index = 0 的位置的数据是否显示，跟barChart.groupBars中的第一个参数有关。
        xValue.add(getResources().getString(R.string.under_construction));
/*        xValue.add("zero");//index = 0 的位置的数据是否显示，跟barChart.groupBars中的第一个参数有关。
        xValue.add("one");
        xValue.add("two");
        xValue.add("three");
        xValue.add("four");*/
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));//设置x轴标签格式化器

        YAxis rightYAxis = doubleBarChar.getAxisRight();
        rightYAxis.setDrawGridLines(false);
        rightYAxis.setEnabled(false);//设置右侧的y轴是否显示。包括y轴的那一条线和上面的标签都不显示
        rightYAxis.setDrawLabels(false);//设置y轴右侧的标签是否显示。只是控制y轴处的标签。控制不了那根线。
        rightYAxis.setDrawAxisLine(false);//这个方法就是专门控制坐标轴线的

        YAxis leftYAxis = doubleBarChar.getAxisLeft();
        leftYAxis.setEnabled(false);
        leftYAxis.setDrawLabels(true);
        leftYAxis.setDrawAxisLine(true);
        leftYAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftYAxis.setDrawGridLines(false);//只有左右y轴标签都设置不显示水平网格线，图形才不会显示网格线
//        leftYAxis.setDrawGridLinesBehindData(true);//设置网格线是在柱子的上层还是下一层（类似Photoshop的层级）
        leftYAxis.setGranularity(1f);//设置最小的间隔，防止出现重复的标签。这个得自己尝试一下就知道了。
        leftYAxis.setAxisMinimum(0);//设置左轴最小值的数值。如果IndexAxisValueFormatter自定义了字符串的话，那么就是从序号为2的字符串开始取值。
        leftYAxis.setSpaceBottom(0);//左轴的最小值默认占有10dp的高度，如果左轴最小值为0，一般会去除0的那部分高度
        //自定义左侧标签的字符串，可以是任何的字符串、中文、英文等
//        leftYAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"0", "1", "2", "3", "4", "5"}));

    }

    private void tofillBarData(ProjectAreaItem projectAreaItem, BarChart doubleBarChar) {
        tv_project_cnt.setText("总数(个):" + projectAreaItem.project_cnt + "");
        tv_already_investment.setText(projectAreaItem.invested + "亿");
        tv_unalready_investment.setText(projectAreaItem.noWorkInvestment + "亿");
        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
        int prepare_project_cnt = projectAreaItem.prepare_project_cnt;
        int construction_project_cnt = projectAreaItem.construction_project_cnt;
        barEntries1.add(new BarEntry(1, prepare_project_cnt));
        barEntries1.add(new BarEntry(2, construction_project_cnt));

        float noWorkInvestment = projectAreaItem.noWorkInvestment;
        float construction_investment = projectAreaItem.construction_investment;

        ArrayList<BarEntry> barEntries2 = new ArrayList<>();
        barEntries2.add(new BarEntry(1, noWorkInvestment));
        barEntries2.add(new BarEntry(2, construction_investment));


        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "项目数");
        barDataSet1.setColor(Color.parseColor("#3476f9"));
        BarDataSet barDataSet2 = new BarDataSet(barEntries2, "投资额");
        barDataSet2.setColor(Color.parseColor("#f6952c"));
        ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();

        iBarDataSets.add(barDataSet1);
        iBarDataSets.add(barDataSet2);

        BarData barData = new BarData(iBarDataSets);
        barDataSet2.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value + "亿";
            }
        });
        barDataSet1.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int) value + "";//通过重写格式化器中的方法，可以设置柱子上面的数据为整数。
            }
        });
        barData.setDrawValues(true);//是否显示柱子的数值
        barData.setValueTextSize(14f);//柱子上面标注的数值的字体大小
        barData.setBarWidth(0.3f);//每个柱子的宽度
        doubleBarChar.setData(barData);
        //如果不设置组直接的距离的话，那么两个柱子会公用一个空间，即发生重叠；另外，设置了各种距离之后，X轴方向会自动调整距离，以保持“两端对齐”
        doubleBarChar.groupBars(0.32f/*从X轴哪个位置开始显示，这个参数具体啥意思。。。*/, 0.32f/*组与组之间的距离*/, 0.05f/*组中每个柱子之间的距离*/);
    }


    private void initOnlyBarChart(BarChart barChar_project_schedule) {
//        Description description = barChar_project_schedule.getDescription();
        barChar_project_schedule.setDescription(null);
//        description.setText("");
//        description.setTextSize(10f);
        barChar_project_schedule.setNoDataText("no data.");
        // 集双指缩放
        barChar_project_schedule.setPinchZoom(false);
        barChar_project_schedule.animateY(2000);
        YAxis rightYAxis = barChar_project_schedule.getAxisRight();
        rightYAxis.setDrawGridLines(false);
        rightYAxis.setEnabled(false);//设置右侧的y轴是否显示。包括y轴的那一条线和上面的标签都不显示
        rightYAxis.setDrawLabels(false);//设置y轴右侧的标签是否显示。只是控制y轴处的标签。控制不了那根线。
        rightYAxis.setDrawAxisLine(false);//这个方法就是专门控制坐标轴线的

        Legend legend = barChar_project_schedule.getLegend();//图例
        legend.setEnabled(false);//是否显示

        YAxis leftYAxis = barChar_project_schedule.getAxisLeft();
        leftYAxis.setEnabled(false);
        leftYAxis.setDrawLabels(true);
        leftYAxis.setDrawAxisLine(true);
        leftYAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftYAxis.setDrawGridLines(false);//只有左右y轴标签都设置不显示水平网格线，图形才不会显示网格线
//        leftYAxis.setDrawGridLinesBehindData(true);//设置网格线是在柱子的上层还是下一层（类似Photoshop的层级）
        leftYAxis.setGranularity(1f);//设置最小的间隔，防止出现重复的标签。这个得自己尝试一下就知道了。
        leftYAxis.setAxisMinimum(0);//设置左轴最小值的数值。如果IndexAxisValueFormatter自定义了字符串的话，那么就是从序号为2的字符串开始取值。
        leftYAxis.setSpaceBottom(0);//左轴的最小值默认占有10dp的高度，如果左轴最小值为0，一般会去除0的那部分高度
        //自定义左侧标签的字符串，可以是任何的字符串、中文、英文等
//        leftYAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8"
//                , "9", "10"}));

    }

    private void toFillBottonBarData(ProjectAreaItem projectAreaItem, BarChart barChar_project_schedule) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        XAxis xAxis = barChar_project_schedule.getXAxis();
        xAxis.setTextSize(15f);
        xAxis.setLabelCount(4, false);//第一个参数是轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        xAxis.setDrawLabels(true);//是否显示X坐标轴上的刻度，默认是true
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x坐标数据的位置
        xAxis.setDrawGridLines(false);//是否显示网格线中与x轴垂直的网格线
        final List<String> xValue = new ArrayList<>();
        xValue.add("zero");//index = 0 的位置的数据在IndexAxisValueFormatter中时不显示的。

        if (projectAreaItem.schedule_summary != null) {
            for (int x = 0; x < projectAreaItem.schedule_summary.size(); ++x) {
                int count = projectAreaItem.schedule_summary.get(x).count;
                String projectextrainfo__schedule = projectAreaItem.schedule_summary.get(x).projectextrainfo__schedule;
                barEntries.add(new BarEntry(x + 1, count));
                xValue.add(projectextrainfo__schedule);
            }
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));//设置x轴标签格式化器


        BarDataSet barDataSet = new BarDataSet(barEntries, "");

        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int) value + "";//通过重写格式化器中的方法，可以设置柱子上面的数据为整数。
            }
        });
        barDataSet.setColor(Color.parseColor("#21a0ee"));

        BarData barData = new BarData(barDataSet);
        barData.setDrawValues(true);//是否显示柱子的数值
        barData.setValueTextSize(14f);//柱子上面标注的数值的字体大小
        barData.setBarWidth(0.5f);//每个柱子的宽度
        barChar_project_schedule.setData(barData);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_projList) {
            SearchActivity.startActivity(this, region);
        }
    }
}
