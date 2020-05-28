package com.dc.module_bbs.projshow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.commonlib.common.MultiTypeSupport;
import com.dc.commonlib.utils.UIUtils;
import com.dc.module_bbs.R;
import com.dc.module_bbs.preview.PreViewActivity;
import com.dc.module_bbs.projsummary.ProjectAreaItem;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectShowAdapter extends BaseRecyclerAdapter<AbsProjectInfo> implements MultiTypeSupport<AbsProjectInfo> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public ProjectShowAdapter(Context context, @Nullable List<AbsProjectInfo> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        this.multiTypeSupport = this;
    }

    private void initPieChart(PieChart pieChart) {
        pieChart.setDescription(null);
//        Description description = pieChart.getDescription();
//        description.setText(""); //设置描述的文字

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
        pieChart.setCenterTextSize(16f);//设置文字的消息
        pieChart.setCenterTextColor(Color.parseColor("#333333"));//设置文字的颜色
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

    private void initPieChartData(PieChart pieChart, ProjectInvestmentInfo projectinvestmentinfo) {
        pieChart.setCenterText(projectinvestmentinfo.investment+"亿\n总投资额(亿)");//设置饼状图中心的文字

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
          if(projectinvestmentinfo.invested!=0){
            pieEntries.add(new PieEntry(projectinvestmentinfo.invested));
        }
        if(projectinvestmentinfo.noWorkInvestment!=0){
            pieEntries.add(new PieEntry(projectinvestmentinfo.noWorkInvestment));
        }

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

    private OnProjectColumnListener onProjectColumnListener;

    public void addOnProjectColumnListener(OnProjectColumnListener onProjectColumnListener) {
        this.onProjectColumnListener = onProjectColumnListener;
    }

    public interface OnProjectColumnListener {
        void onProjOverviewClick(ProjectItemBean itemBean);

        void onVideoMonitoring(ProjectItemBean itemBean);

        void onLaborClick(ProjectItemBean itemBean);

        void onTowerCraneMonitoringClick(ProjectItemBean itemBean);
    }

    @Override
    protected void convert(BaseViewHolder holder, final AbsProjectInfo item, int position, List<Object> payloads) {
        if (item instanceof BannerProjectInfo) {
            final Banner banner = holder.getView(R.id.banner);
            banner.setImageLoader(new GlideImageLoader());
            final BannerProjectInfo bannerProjectInfo = (BannerProjectInfo) item;
            banner.setImages(bannerProjectInfo.urls);
            banner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(3000);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CENTER);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    String[] array2 = bannerProjectInfo.urls.toArray(new String[bannerProjectInfo.urls.size()]);
                    PreViewActivity.startActivity(getContext(), array2, position);
                }
            });
            //banner设置方法全部调用完毕时最后调用
            banner.start();

        } else if (item instanceof ProjectInfoDetail) {
            final ProjectInfoDetail projectInfoDetail = (ProjectInfoDetail) item;
            TextView tv_proj_adress = holder.getView(R.id.tv_proj_adress);
            TextView tv_proj_adressinfo = holder.getView(R.id.tv_proj_adressinfo);
            TextView tv_planned_start_time = holder.getView(R.id.tv_planned_start_time);
            TextView tv_actual_start_time = holder.getView(R.id.tv_actual_start_time);
            TextView tv_planned_completion_time = holder.getView(R.id.tv_planned_completion_time);
            tv_proj_adress.setText(projectInfoDetail.projectAdressName);
            tv_proj_adressinfo.setText(projectInfoDetail.projectAdress);
            if (TextUtils.isEmpty(projectInfoDetail.startsTime)) {
                tv_planned_start_time.setText("-");
            } else {
                tv_planned_start_time.setText(projectInfoDetail.startsTime);
            }
            if (TextUtils.isEmpty(projectInfoDetail.actualConstructionTime)) {
                tv_actual_start_time.setText("-");

            } else {
                tv_actual_start_time.setText(projectInfoDetail.actualConstructionTime);

            }
            if (TextUtils.isEmpty(projectInfoDetail.endTime)) {
                tv_planned_completion_time.setText("-");

            } else {
                tv_planned_completion_time.setText(projectInfoDetail.endTime);

            }

            holder.getView(R.id.fl_proj_overview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onProjectColumnListener && item.projectItemBean != null) {
                        onProjectColumnListener.onProjOverviewClick(item.projectItemBean);
                    }
                }
            });
            holder.getView(R.id.fl_video_monitoring).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onProjectColumnListener && item.projectItemBean != null) {
                        onProjectColumnListener.onVideoMonitoring(item.projectItemBean);
                    }
                }
            });
            holder.getView(R.id.fl_labor).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (null != onProjectColumnListener && item.projectItemBean != null) {
                        onProjectColumnListener.onLaborClick(item.projectItemBean);
                    }

                }
            });
            holder.getView(R.id.fl_towerCrane_monitoring).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onProjectColumnListener && item.projectItemBean != null) {
                        onProjectColumnListener.onTowerCraneMonitoringClick(item.projectItemBean);
                    }
                }
            });


        } else if (item instanceof ProjectInvestmentInfo) {
            ProjectInvestmentInfo projectInvestmentInfo = (ProjectInvestmentInfo) item;
            PieChart picChart = holder.getView(R.id.picChart);
            TextView tv_already_investment = holder.getView(R.id.tv_already_investment);
            TextView tv_unalready_investment = holder.getView(R.id.tv_unalready_investment);
            tv_already_investment.setText(projectInvestmentInfo.invested + "亿");
            tv_unalready_investment.setText(projectInvestmentInfo.noWorkInvestment + "亿");
            initPieChart(picChart);
            initPieChartData(picChart, projectInvestmentInfo);

        } else if (item instanceof ProjectInvestmentItem.ProjectInvestmentItemBean) {
            ProjectInvestmentItem.ProjectInvestmentItemBean projectInvestmentItem = (ProjectInvestmentItem.ProjectInvestmentItemBean) item;
            View view_line_bg = holder.getView(R.id.view_line_bg);
            ImageView iv_state = holder.getView(R.id.iv_state);
            TextView tv_title = holder.getView(R.id.tv_title);
            TextView tv_complate_time = holder.getView(R.id.tv_complate_time);
            TextView tv_phase_title = holder.getView(R.id.tv_phase_title);
            if (projectInvestmentItem.phase) {  //节点
                tv_complate_time.setVisibility(View.VISIBLE);
                tv_complate_time.setText(projectInvestmentItem.real_date);
                iv_state.setVisibility(View.VISIBLE);
                tv_phase_title.setVisibility(View.VISIBLE);
                tv_title.setVisibility(View.GONE);
                tv_phase_title.setText(projectInvestmentItem.name);
                view_line_bg.setVisibility(View.GONE);
                if (projectInvestmentItem.finished) {
                    //finish 已完成
                    tv_phase_title.setTextColor(getContext().getResources().getColor(R.color.text_color_3476f9));
                    if (UIUtils.dateToStamp(projectInvestmentItem.real_date) > UIUtils.dateToStamp(projectInvestmentItem.plane_date)) {
                        //todo 超时
                        iv_state.setImageResource(R.drawable.delay);
                    } else {
                        iv_state.setImageResource(R.drawable.completed);
                    }
                } else {
                    tv_phase_title.setTextColor(getContext().getResources().getColor(R.color.text_color5_333333));
                    if (UIUtils.dateToStamp(projectInvestmentItem.real_date) > UIUtils.dateToStamp(projectInvestmentItem.plane_date)) {
                        //todo 超时
                        iv_state.setImageResource(R.drawable.delay);
                    } else {
                        iv_state.setImageResource(R.drawable.notstarted);
                    }
                }
            } else if (projectInvestmentItem.isFalseData) {
                view_line_bg.setVisibility(View.VISIBLE);
                tv_complate_time.setVisibility(View.INVISIBLE);
                iv_state.setVisibility(View.INVISIBLE);
                tv_phase_title.setVisibility(View.INVISIBLE);
                tv_title.setVisibility(View.GONE);
            } else {
                view_line_bg.setVisibility(View.VISIBLE);
                tv_complate_time.setVisibility(View.INVISIBLE);
                tv_phase_title.setVisibility(View.GONE);
                tv_title.setVisibility(View.VISIBLE);
                tv_title.setText(projectInvestmentItem.name);
                iv_state.setVisibility(View.INVISIBLE);
                if (projectInvestmentItem.finished) {
                    tv_title.setTextColor(getContext().getResources().getColor(R.color.text_color_3476f9));
                    Drawable drawableLeft = getContext().getResources().getDrawable(
                            R.drawable.choice);
                    tv_title.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
//                    tv_title.setCompoundDrawablePadding(4);
                } else {
                    tv_title.setTextColor(getContext().getResources().getColor(R.color.color_ababbb));
                    Drawable drawableLeft = getContext().getResources().getDrawable(
                            R.drawable.nochoice);
                    tv_title.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
//                    tv_title.setCompoundDrawablePadding(4);
                }

            }


        }
    }

    @Override
    public int getLayoutId(AbsProjectInfo item, int position) {
        if (item instanceof BannerProjectInfo) {
            return R.layout.proj_show_item_banner;
        } else if (item instanceof ProjectInfoDetail) {
            return R.layout.proj_show_projinfo;
        } else if (item instanceof ProjectInvestmentInfo) {
            return R.layout.proj_show_projj_pie;
        } else if (item instanceof ProjectInvestmentItem.ProjectInvestmentItemBean) {
//            return R.layout.proj_item_progress_items;
            return R.layout.proj_item_prohgress_itemz;
        }
        return 0;
    }
}
