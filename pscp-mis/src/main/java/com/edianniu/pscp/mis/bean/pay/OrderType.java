/**
 * 
 */
package com.edianniu.pscp.mis.bean.pay;

/**
 * @author cyl
 *
 */
public enum OrderType {
	RECHARGE(1, "充值"){
		public String getOrderTitle(){
			return "充值";
		}
		public String getOrderBody(){
			return "充值";
		}
		
	},SOCIAL_WORK_ORDER_PAY(2, "工单支付"){
		public String getOrderTitle(){
			return "工单支付";
		}
		public String getOrderBody(){
			return "工单支付";
		}
		
	}
	,SOCIAL_ELECTRICIAN_WORK_ORDER_SETTLEMENT(3, "工单结算"){
		public String getOrderTitle(){
			return "工单结算";
		}
		public String getOrderBody(){
			return "工单结算";
		}
		
	}
	,NEEDS_ORDER_PAY(4, "参与需求保证金"){
		public String getOrderTitle(){
			return "参与需求保证金";
		}
		public String getOrderBody(){
			return "参与需求保证金支付订单";
		}
		
	}
	,PROJECT_SETTLEMENT(5, "项目结算"){
		public String getOrderTitle(){
			return "项目结算";
		}
		public String getOrderBody(){
			return "项目结算";
		}
		
	}
	,RENTER_CHARGE_SETTLEMENT(6, "电费结算"){
		public String getOrderTitle(){
			return "电费结算";
		}
		public String getOrderBody(){
			return "电费结算";
		}
		
	};
	private int value;
	
	private String desc;

	OrderType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	public String getOrderTitle(){return "";};
	public String getOrderBody(){return "";};
	public static OrderType parse(Integer value){
		if(value==null){
    		return SOCIAL_WORK_ORDER_PAY;
    	}
    	for(OrderType orderType:OrderType.values()){
    		if(orderType.getValue()==value){
    			return orderType;
    		}
    	}
    	return SOCIAL_WORK_ORDER_PAY;
	}
    
    public static boolean include(Integer value){
    	if(value==null){
    		return false;
    	}
    	for(OrderType orderType:OrderType.values()){
    		if(orderType.getValue()==value){
    			return true;
    		}
    	}
    	return false;
    }
    public static String tip(){
    	StringBuffer sb=new StringBuffer();
    	sb.append("orderType 只能是");
    	for(OrderType orderType:OrderType.values()){
    		sb.append(orderType.getValue()).append("[").append(orderType.getDesc()).append("]").append(",");
    	}
    	return sb.toString().substring(0, sb.length()-1);
    }
	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	

}
