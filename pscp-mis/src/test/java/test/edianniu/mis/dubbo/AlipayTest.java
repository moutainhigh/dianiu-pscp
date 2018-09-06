/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月25日 下午3:56:31 
 * @version V1.0
 */
package test.edianniu.mis.dubbo;

import com.edianniu.pscp.mis.util.MoneyUtils;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月25日 下午3:56:31 
 * @version V1.0
 */
public class AlipayTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String result ="{\"alipay_trade_app_pay_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"app_id\":\"2017051607253335\",\"auth_app_id\":\"2017051607253335\",\"charset\":\"utf-8\",\"timestamp\":\"2017-07-25 15:25:29\",\"total_amount\":\"0.01\",\"trade_no\":\"2017072521001004350234606478\",\"seller_id\":\"2088021780335478\",\"out_trade_no\":\"CD787316933787720\"},\"sign\":\"MswE2AsHyrdk0GcanLtDvJw0YGfhpk6ZmTr5kqAB\/tNg7flv3fmjjwx0ssftBDvouHn3ERm\/SM\/jPLFoBPfwXwClG5kyDpZHMvuBDnqvIm8tGsruTe+YAMHrgiuyo3nH8LXzS5Nib\/cCRoij7KVnBKaZ+FszT4ggcEqsoPfzmcFmWmz6rxZJ5TqqOwjTr9+2Z5PzUYra1OAk3oVw\/kvivb0j+3V6vEaBzJnigwXbkedu9gkSJ42e75VrtDAF4QYfX\/Sd0prI8Xmuxv6dLZ5vLercHWEbB78KYg8+ZG1Rldwux1rbubJZj7\/xoC4cPMeDXIGkNVN8qmGurbLW2NV7vw==\",\"sign_type\":\"RSA2\"}";

		//Map<String, String> params = AlipayCore.parseParams(result);
		double totalAmount=9609.88D;
		double newAmount1=MoneyUtils.add(totalAmount, 0.01D);
		System.out.println(totalAmount*100D);
		int amount = (int) (totalAmount * 100D);
		System.out.println("xxx="+amount);
		amount += 0.01D*100;
		// 转换单位为元.
		double newAmount = amount / 100d;
		System.out.println(newAmount);
		System.out.println(MoneyUtils.sub(totalAmount, 0.05D));
	}

}
