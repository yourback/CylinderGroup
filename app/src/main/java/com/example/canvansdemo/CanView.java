package com.example.canvansdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class CanView extends View {
    // number of group
    private int num_of_group_x;

    // y axis text num
    private int num_of_y_axis_text;
    // unit of y
    private String unit_of_y_axis;
    // size of each grid
    private int size_of_each_grid_y;

    // 坐标系画笔
    private Paint mAxisPaint;
    private int ratio = 1;

    // sleep time paint
    private Paint fall_in_sleep_time_cylinder_paint;
    // fall in sleep time paint color
    private int color_of_fall_in_sleep_time_cylinder;
    // fall in sleep time paint
    private Paint sleep_time_cylinder_paint;
    // sleep time paint color
    private int color_of_sleep_time_cylinder;


    // x axis
    // axis text paint
    private Paint x_axis_text_paint;

    // y axis
    // axis text paint
    private Paint y_axis_text_paint;

    // axis common
    // textsize of axis text
    private int text_size_of_axis_text = 10;
    // color of axis text
    private int color_of_axis_text;
    // axis stroke width
    private int axis_stroke_width = 4;


    // axis bottom circle
    // paint
    private Paint axis_bottom_circle_paint;
    // radius of small
    private int radius_of_small_circle;
    private int radius_of_big_circle;
    private int color_of_group_circle;

    // tool rect  to  get rect of date text
    Rect date_text_rect;

    // two list-data
    private List<Integer> sleep_time_list;
    private List<Integer> fall_in_sleep_time_list;

    // axis line color
    private int color_of_axis_line;


    public CanView(Context context) {
        super(context);
        initPaint();
    }

    public CanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CanView);
        num_of_y_axis_text = ta.getInteger(R.styleable.CanView_num_of_group_y, 10) + 1;
        unit_of_y_axis = ta.getString(R.styleable.CanView_unit_of_y_axis);
        size_of_each_grid_y = ta.getInteger(R.styleable.CanView_size_of_each_grid_y, 2);


        color_of_sleep_time_cylinder = ta.getColor(R.styleable.CanView_color_of_sleep_time_cylinder, 0);
        color_of_fall_in_sleep_time_cylinder = ta.getColor(R.styleable.CanView_color_of_fall_in_sleep_time_cylinder, 0);
        color_of_axis_line = ta.getColor(R.styleable.CanView_color_of_axis_line, 0);
        color_of_group_circle = ta.getColor(R.styleable.CanView_color_of_group_circle, 0);
        color_of_axis_text = ta.getColor(R.styleable.CanView_color_of_axis_text, 0);
        ratio = ta.getInteger(R.styleable.CanView_ratio, 1);
        radius_of_small_circle = ta.getInteger(R.styleable.CanView_radius_of_small_circle, 3);
        radius_of_big_circle = ta.getInteger(R.styleable.CanView_radius_of_big_circle, 5);
        text_size_of_axis_text = ta.getInteger(R.styleable.CanView_textsize_of_axis_text, 10);

        ta.recycle();

        initPaint();

        date_text_rect = new Rect();

        initData();
    }

    private void initData() {
        sleep_time_list = new ArrayList<>();
        sleep_time_list.add(1);
        sleep_time_list.add(2);
        sleep_time_list.add(3);
        fall_in_sleep_time_list = new ArrayList<>();
        fall_in_sleep_time_list.add(5);
        fall_in_sleep_time_list.add(10);
        fall_in_sleep_time_list.add(15);
    }


    public void loadData(List<Integer> fall_in_sleep_time_list, List<Integer> sleep_time_list) {

        // set num of x axis
        num_of_group_x = fall_in_sleep_time_list.size() > sleep_time_list.size() ? fall_in_sleep_time_list.size() : sleep_time_list.size();

        this.fall_in_sleep_time_list.clear();
        this.sleep_time_list.clear();
        this.fall_in_sleep_time_list = fall_in_sleep_time_list;
        this.sleep_time_list = sleep_time_list;

        // render again
        this.invalidate();
    }


    private void initPaint() {
        axis_stroke_width = 4;

        // mAxisPaint
        mAxisPaint = new Paint();
        // color
        mAxisPaint.setColor(color_of_axis_line == 0 ? Color.parseColor("#C7C7C7") : color_of_axis_line);
        // bold
        mAxisPaint.setStrokeWidth(4);
        // 平滑
        mAxisPaint.setAntiAlias(true);
        // 填充样式
        mAxisPaint.setStyle(Paint.Style.FILL_AND_STROKE);


        // fall_in_sleep_time_cylinder_paint
        fall_in_sleep_time_cylinder_paint = new Paint();
        fall_in_sleep_time_cylinder_paint.setAntiAlias(true);
        fall_in_sleep_time_cylinder_paint.setColor(color_of_fall_in_sleep_time_cylinder == 0 ? Color.parseColor("#2A93D5") : color_of_fall_in_sleep_time_cylinder);
        fall_in_sleep_time_cylinder_paint.setStrokeWidth(1);
        fall_in_sleep_time_cylinder_paint.setStyle(Paint.Style.FILL);


        // sleep_time_cylinder_paint
        sleep_time_cylinder_paint = new Paint();
        sleep_time_cylinder_paint.setAntiAlias(true);
        sleep_time_cylinder_paint.setColor(color_of_sleep_time_cylinder == 0 ? Color.parseColor("#37CAEC") : color_of_sleep_time_cylinder);
        sleep_time_cylinder_paint.setStrokeWidth(1);
        sleep_time_cylinder_paint.setStyle(Paint.Style.FILL);


        // x_axis_text_paint
        x_axis_text_paint = new Paint();
        x_axis_text_paint.setAntiAlias(true);
        x_axis_text_paint.setColor(color_of_axis_text == 0 ? Color.parseColor("#4E4E4E") : color_of_group_circle);
        x_axis_text_paint.setStrokeWidth(1);
        x_axis_text_paint.setStyle(Paint.Style.FILL);
        x_axis_text_paint.setTextAlign(Paint.Align.CENTER);
        x_axis_text_paint.setTextSize(ScaleUtils.dip2px(getContext(), text_size_of_axis_text));

        // y_axis_text_paint
        y_axis_text_paint = new Paint();
        y_axis_text_paint.setAntiAlias(true);
        y_axis_text_paint.setColor(color_of_axis_text == 0 ? Color.parseColor("#4E4E4E") : color_of_group_circle);
        y_axis_text_paint.setStrokeWidth(1);
        y_axis_text_paint.setStyle(Paint.Style.FILL);
        y_axis_text_paint.setTextAlign(Paint.Align.LEFT);
        y_axis_text_paint.setTextSize(ScaleUtils.dip2px(getContext(), text_size_of_axis_text));


        // axis_bottom_circle_paint
        axis_bottom_circle_paint = new Paint();
        axis_bottom_circle_paint.setAntiAlias(true);
        axis_bottom_circle_paint.setColor(color_of_group_circle == 0 ? Color.parseColor("#C1C1C1") : color_of_group_circle);
        axis_bottom_circle_paint.setStrokeWidth(1);
        axis_bottom_circle_paint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        // start_date_x
        int start_date_x = 0;
        int end_date_x = 0;


        // 画两根线
        // 横轴
//        canvas.drawLine(axis_cross_x, axis_cross_y, getMeasuredWidth() - getPaddingEnd(), axis_cross_y, mAxisPaint);
        // 纵轴
//        canvas.drawLine(axis_cross_x, axis_cross_y, axis_cross_x, getPaddingTop(), mAxisPaint);
        // y axis text
        for (int i = 0; i < num_of_y_axis_text; i++) {
            int y = (int) (axis_cross_y - i * y_min_unit);
            canvas.drawLine(axis_cross_x, y, getMeasuredWidth() - getPaddingEnd(), y, mAxisPaint);
            canvas.drawText((size_of_each_grid_y * i) + (TextUtils.isEmpty(unit_of_y_axis) ? "h" : unit_of_y_axis), getPaddingStart(), y, y_axis_text_paint);
        }


        // traversing num of group
        for (int i = 0; i < num_of_group_x; i++) {
            // paint one group rect
            // fall in sleep time cylinder
            int fall_in_sleep_time_start_x = i * group_width + 2 * min_unit + axis_cross_x;
            // calculate hight of fall in sleep time data
            float hight_of_tall_in_sleep_time_date = 0;
            if (i < fall_in_sleep_time_list.size()) {
                hight_of_tall_in_sleep_time_date = ((float) fall_in_sleep_time_list.get(i)) / size_of_each_grid_y;
            }
            canvas.drawRect(fall_in_sleep_time_start_x, axis_cross_y - (axis_stroke_width >> 1), (int) (fall_in_sleep_time_start_x + cylinder_with), (axis_cross_y - (axis_stroke_width >> 1) - y_min_unit * hight_of_tall_in_sleep_time_date), fall_in_sleep_time_cylinder_paint);


            // sleep time cylinder
            int sleep_time_start_x = i * group_width + min_unit + axis_cross_x;
            // calculate hight of sleep time data
            float sleep_time_date = 0;
            if (i < sleep_time_list.size()) {
                sleep_time_date = ((float) sleep_time_list.get(i)) / size_of_each_grid_y;
            }
            canvas.drawRect(sleep_time_start_x, axis_cross_y - (axis_stroke_width >> 1), (int) (sleep_time_start_x + cylinder_with), axis_cross_y - (axis_stroke_width >> 1) - y_min_unit * sleep_time_date, sleep_time_cylinder_paint);


            int sleep_time_axis_text_start_x = i * group_width + group_width / 2 + axis_cross_x;
            int circle_center_y = axis_cross_y + ScaleUtils.dip2px(getContext(), (float) (radius_of_big_circle * 2.5));
            if (i == 0 || i == num_of_group_x - 1) {
                if (i == 0) {
                    start_date_x = sleep_time_axis_text_start_x;
                } else {
                    end_date_x = sleep_time_axis_text_start_x;
                }
                // date axis text layout.center
                canvas.drawText("9/" + (i + 1), sleep_time_axis_text_start_x, axis_cross_y + ScaleUtils.dip2px(getContext(), text_size_of_axis_text + radius_of_big_circle * 4), x_axis_text_paint);
                // big circle
                canvas.drawCircle(sleep_time_axis_text_start_x, circle_center_y, ScaleUtils.dip2px(getContext(), radius_of_big_circle), axis_bottom_circle_paint);
            } else {
                // small circle
                canvas.drawCircle(sleep_time_axis_text_start_x, circle_center_y, ScaleUtils.dip2px(getContext(), radius_of_small_circle), axis_bottom_circle_paint);
            }
        }


        // tip of cylinder info
        int date_middle_width = end_date_x - start_date_x;
        // two cylinders color shoulder divided into three parts
        int date_middle_width_unit = date_middle_width / 3;
        // traversing to paint cylinder info

        // measure date text center help paint circle

        y_axis_text_paint.getTextBounds("入睡", 0, "入睡".length(), date_text_rect);
        int half_width = (ScaleUtils.dip2px(getContext(), radius_of_big_circle) + date_text_rect.width()) / 2;
        Paint.FontMetrics fontMetrics = y_axis_text_paint.getFontMetrics();
        // (fontMetrics.descent - fontMetrics.ascent) / 2;

        for (int i = 1; i < 3; i++) {
            int base_x = start_date_x + date_middle_width_unit * i;

            /*
             *  paint color circle
             *  base_x - half_width - ScaleUtils.dip2px(getContext(), 2 * radius_of_big_circle)
             *  y equal date`s y     axis_cross_y + ScaleUtils.dip2px(getContext(), text_size_of_axis_text + radius_of_big_circle * 4)
             *
             * */
            if (i == 1)
                canvas.drawCircle(base_x - half_width - ScaleUtils.dip2px(getContext(), radius_of_big_circle), axis_cross_y - (fontMetrics.bottom - fontMetrics.top) / 2 + fontMetrics.bottom + ScaleUtils.dip2px(getContext(), text_size_of_axis_text + radius_of_big_circle * 4), ScaleUtils.dip2px(getContext(), radius_of_big_circle), sleep_time_cylinder_paint);
            else
                canvas.drawCircle(base_x - half_width - ScaleUtils.dip2px(getContext(), radius_of_big_circle), axis_cross_y - (fontMetrics.bottom - fontMetrics.top) / 2 + fontMetrics.bottom + ScaleUtils.dip2px(getContext(), text_size_of_axis_text + radius_of_big_circle * 4), ScaleUtils.dip2px(getContext(), radius_of_big_circle), fall_in_sleep_time_cylinder_paint);
            /*
             *  paint cylinder info text
             *  base_x + half_width - date_text_rect.width()
             *  y equal date`s y     axis_cross_y + ScaleUtils.dip2px(getContext(), text_size_of_axis_text + radius_of_big_circle * 4)
             *
             * */
            String cylinder_info;
            if (i == 1) {
                cylinder_info = "入睡";
            } else {
                cylinder_info = "睡眠";
            }
            canvas.drawText(cylinder_info, base_x + half_width - date_text_rect.width(), axis_cross_y + ScaleUtils.dip2px(getContext(), text_size_of_axis_text + radius_of_big_circle * 4), y_axis_text_paint);
        }
    }


    // xy轴的交叉点
    private int axis_cross_x;
    private int axis_cross_y;

    // group width
    private int group_width;

    // min unit
    private int min_unit;

    // cylinder width
    private float cylinder_with;

    // y min_unit
    private float y_min_unit;


    private void initParams() {
        // 腾出来底部 显示坐标
        int bottom_px = ScaleUtils.dip2px(getContext(), text_size_of_axis_text + radius_of_big_circle * 4);

        int start_px = ScaleUtils.dip2px(getContext(), text_size_of_axis_text + radius_of_big_circle * 2);

        int width = getMeasuredWidth() - getPaddingEnd() - getPaddingStart();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        axis_cross_x = getPaddingStart() + start_px;
        axis_cross_y = height + getPaddingTop() - bottom_px;

        // x axis long
        // x轴长度
        int axis_long_x = width - start_px;
        // y axis long
        // y 轴长度
        int axis_long_y = height - bottom_px;
        // get group width
        group_width = axis_long_x / num_of_group_x;

        // min unit
        min_unit = group_width / 4;

        // cylinder width
        cylinder_with = min_unit;

        // y min_unit
        y_min_unit = axis_cross_y / num_of_y_axis_text;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec, width);
        //设置宽高
        setMeasuredDimension(width, height);
        initParams();
    }

    //根据xml的设定获取宽度

    private int measureWidth(int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);

        int specSize = MeasureSpec.getSize(measureSpec);

        //wrap_content
//
//        if (specMode == MeasureSpec.AT_MOST) {
//
//        }

        //fill_parent或者精确值

//        else if (specMode == MeasureSpec.EXACTLY) {
//
//        }

//        Log.i("这个控件的宽度----------", "specMode=" + specMode + " specSize=" + specSize);

        return specSize;

    }

    //根据xml的设定获取高度

    private int measureHeight(int measureSpec, int width) {

        int specMode = MeasureSpec.getMode(measureSpec);

        int specSize = MeasureSpec.getSize(measureSpec);

//        //wrap_content
//        if (specMode == MeasureSpec.AT_MOST) {
//        }
//        //fill_parent或者精确值
//        else if (specMode == MeasureSpec.EXACTLY) {
//        }
        specSize = width / ratio;
//        Log.i("这个控件的高度----------", "specMode:" + specMode + " specSize:" + specSize);
        return specSize;

    }
}
