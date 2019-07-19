package com.zld.adapter;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zld.R;
import com.zld.bean.AllOrder;
import com.zld.lib.util.SharedPreferencesUtils;
import com.zld.lib.util.TimeTypeUtil;
import java.util.ArrayList;


public class CurrentOrderAdapter extends BaseAdapter {
	private String orderid;
	private Context context;
	private int selectedPosition = -1;
	public ArrayList<AllOrder> orders;

	private static final String TAG = "CurrentOrderAdapter";



	public CurrentOrderAdapter(Context context, ArrayList<AllOrder> orders) {
		this.context = context;
		if (orders == null){
			this.orders = new ArrayList<AllOrder>();
		}
	}

	public void setSelectedPosition(int i) {
		selectedPosition = i;
		notifyDataSetChanged();
	}


	public void highLightSelectedItem(int position){
		selectedPosition = position - 1;
		notifyDataSetChanged();
	}

	public void changeItemContent(String carNumber) {
		if (orders != null && orders.size() > selectedPosition){
			AllOrder order = orders.get(selectedPosition);
			order.setCarnumber(carNumber);
			notifyDataSetChanged();
		}
	}

	//设置列表
	public void setOrder(AllOrder order) {

		if (orders == null) {
			removeOrders();
			return;
		}
		this.orders.clear();
		this.orders.add( order );
		Log.i(TAG, "this.orders添加数据" + (orders != null ? orders.toString() : ""));
		notifyDataSetChanged();
	}

  	//设置列表
    public void setOrders(ArrayList<AllOrder> orders) {


        if (orders == null) {
            removeOrders();
            return;
        }

        this.orders.clear();
        this.orders = orders;
        Log.i(TAG, "this.orders添加数据" + (orders != null ? orders.toString() : ""));
        notifyDataSetChanged();
    }

	//添加到后面
	public void addOrders(AllOrder orders) {
		if (this.orders == null) {
			Log.i(TAG, "添加的数据null");
		} else {

			if(orders != null){
				ArrayList<AllOrder> new_Orders = new ArrayList<AllOrder>();
				new_Orders.add(orders);
				new_Orders.addAll(this.orders);

				this.orders=new_Orders;
				Log.i(TAG, "有本地数据。this.orders添加数据" + orders.toString());
			}
		}
		notifyDataSetChanged();
	}

	//添加到后面
	public void addOrders(ArrayList<AllOrder> orders) {
		if (this.orders == null) {
            Log.i(TAG, "添加的数据null");
		} else {

			if(orders != null){
				ArrayList<AllOrder> new_Orders = new ArrayList<AllOrder>();
				new_Orders.addAll(this.orders);
				new_Orders.addAll(orders);
				this.orders=new_Orders;
                Log.i(TAG, "有本地数据。this.orders添加数据" + orders.toString());
			}
		}
		notifyDataSetChanged();
	}

	//移除所有并刷新
	public void removeOrders() {
		if (this.orders != null) {
			Log.e(TAG, "清空Orders");
			this.orders.clear();
			this.notifyDataSetChanged();
		}
	}

	public String getOrderid() {
		return orderid;
	}

    //返回对应的订单
    public AllOrder getAllOrders(int position) {
        if(orders.size() == 0){
            return null;
        }
        AllOrder allOrder = orders.get(position - 1);
        return allOrder;
    }

	@Override
	public int getCount() {
		return orders.size();
	}

	@Override
	public Object getItem(int position) {
		return orders.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public boolean isShowMonthUser() {
		return SharedPreferencesUtils.getParam(context, "zld_config", "isshowmonthcard", false);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.car_detail_item, null);
			holder = new ViewHolder();
            holder.tv_car_number = convertView.findViewById(R.id.tv_car_number);
            holder.tv_enter_time = convertView.findViewById(R.id.tv_enter_time);
            holder.iv_monthuser = convertView.findViewById(R.id.iv_monthuser);
            holder.iv_monthuserSec = convertView.findViewById(R.id.iv_monthusersec);
            holder.iv_monthusercycle = convertView.findViewById(R.id.iv_monthusercycle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (orders != null&&orders.get(position) != null) {
			if (orders.get(position).getBtime() != null&&!orders.get(position).getBtime().equals("null")) {
				//本地化相关
				String btime = orders.get(position).getBtime();
				// 2015-12-13 12:13
				if(btime.matches("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{2}:\\d{2}")){
					Log.e(TAG,"btime:"+btime);
					Long stringTime = TimeTypeUtil.getLongTime(btime);
					Log.e(TAG,"stringTime:"+stringTime);
					String begindate = TimeTypeUtil.getStringTime(stringTime);
					Log.e(TAG,"begindate:"+begindate);
					holder.tv_enter_time.setText(begindate);
				}else{
					// 12-13 12:13
					holder.tv_enter_time.setText(btime);
				}
			}
			if (orders.get(position).getCarnumber() != null
					&& !orders.get(position).getCarnumber().equals("null")) {
				Log.e(TAG, orders.get(position).getCarnumber());
				holder.tv_car_number.setText(orders.get(position)
						.getCarnumber());
			}
			if(orders.get(position).getCtype()!=null&&
					orders.get(position).getCtype().equals("5")&&isShowMonthUser()){
				holder.iv_monthuser.setVisibility(View.VISIBLE);
				holder.iv_monthuserSec.setVisibility(View.GONE);
				holder.iv_monthusercycle.setVisibility(View.GONE);
			}else if(orders.get(position).getCtype()!=null&&
					orders.get(position).getCtype().equals("7")&&isShowMonthUser()){
				holder.iv_monthuserSec.setVisibility(View.VISIBLE);
				holder.iv_monthuser.setVisibility(View.GONE);
				holder.iv_monthusercycle.setVisibility(View.GONE);
			}else if(orders.get(position).getCtype()!=null&&
					orders.get(position).getCtype().equals("8")&&isShowMonthUser()){
				holder.iv_monthusercycle.setVisibility(View.VISIBLE);
				holder.iv_monthuserSec.setVisibility(View.GONE);
				holder.iv_monthuser.setVisibility(View.GONE);
			}else {
				holder.iv_monthuser.setVisibility(View.GONE);
				holder.iv_monthuserSec.setVisibility(View.GONE);
				holder.iv_monthusercycle.setVisibility(View.GONE);
			}
		}
		if (selectedPosition == position) {
			convertView.setBackgroundColor(0xFF2764e6);
			holder.tv_car_number.setTextColor(Color.WHITE);
			holder.tv_enter_time.setTextColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(0xFFebebeb);
			holder.tv_car_number.setTextColor(Color.BLACK);
			holder.tv_enter_time.setTextColor(Color.BLACK);
		}

		return convertView;
	}

	private static class ViewHolder {
		ImageView iv_monthusercycle;
		TextView tv_car_number;
		TextView tv_enter_time;
		ImageView iv_monthuser;
		ImageView iv_monthuserSec;
	}




}
