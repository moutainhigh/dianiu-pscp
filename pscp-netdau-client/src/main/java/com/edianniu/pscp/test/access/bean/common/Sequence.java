/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午7:40:58 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.bean.common;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午7:40:58 
 * @version V1.0
 */
public class Sequence implements Serializable{
	private static final long serialVersionUID = 1L;
	private static AtomicInteger SEQUENCE = new AtomicInteger(10000000);
	public static int get() {
		int no = SEQUENCE.incrementAndGet();
		while (no >= 99999999) {
			SEQUENCE.compareAndSet(no, 10000000);
			no = SEQUENCE.incrementAndGet();
		}
		return no;
	}
	
}
