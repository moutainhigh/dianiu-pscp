package com.edianniu.pscp.das.meter.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 功率因数电费调整表工具类
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月28日 下午4:44:56
 * @version V1.0
 */
public class PowerFactorUtils {
	private final static Map<String, ChargeAdjustmentTable> chargeAdjustmentTableMap = new HashMap<>();
	static {
		ChargeAdjustmentTable t90=new ChargeAdjustmentTable_90();
		ChargeAdjustmentTable t85=new ChargeAdjustmentTable_85();
		ChargeAdjustmentTable t80=new ChargeAdjustmentTable_80();
		chargeAdjustmentTableMap.put(String.valueOf(t90.getStandardPowerFactor()), t90);
		chargeAdjustmentTableMap.put(String.valueOf(t85.getStandardPowerFactor()), t85);
		chargeAdjustmentTableMap.put(String.valueOf(t80.getStandardPowerFactor()), t80);
		
	}

	/**
	 * 根据标准功率因数和实际功率因数
	 * 
	 * @param standardPowerFactor
	 * @param activePowerFactor
	 * @return
	 */
	public static Double getAdjustmentRate(Double standardPowerFactor,
			Double activePowerFactor) {
		ChargeAdjustmentTable chargeAdjustmentTable = chargeAdjustmentTableMap
				.get(String.valueOf(standardPowerFactor));
		if (chargeAdjustmentTable != null) {
			return chargeAdjustmentTable.getAdjustmentRate(activePowerFactor);
		}
		return 0.00;
	}

	static abstract class ChargeAdjustmentTable {
		private Double standardPowerFactor = 0.90;

		ChargeAdjustmentTable(Double standardPowerFactor) {
			this.standardPowerFactor = standardPowerFactor;
		}

		protected abstract Double getAdjustmentRate(Double activePowerFactor);

		public Double getStandardPowerFactor() {
			return standardPowerFactor;
		}
	}

	/**
	 * 0.90标准
	 * 
	 * @author yanlin.chen
	 * @email yanlin.chen@edianniu.com
	 * @date 2017年12月28日 下午5:44:55
	 * @version V1.0
	 */
	static class ChargeAdjustmentTable_90 extends ChargeAdjustmentTable {

		public ChargeAdjustmentTable_90() {
			super(0.90);
		}

		@Override
		protected Double getAdjustmentRate(Double activePowerFactor) {
			if (activePowerFactor <= 0.64) {
				return ((super.standardPowerFactor - activePowerFactor) / 0.01) * 2;// 增加电费
			} else if (activePowerFactor < super.standardPowerFactor
					&& activePowerFactor >= 0.70) {// 增加电费
				return (super.standardPowerFactor - activePowerFactor) * 100 * 0.5;
			} else if (activePowerFactor >= 0.69 && activePowerFactor <= 0.65) {// 增加电费
				return (0.70 - activePowerFactor) * 100 * 1 + 10;
			} else if (activePowerFactor >= super.standardPowerFactor
					&& activePowerFactor <= 0.94) {// 减少电费
				return -(activePowerFactor - super.standardPowerFactor) * 100 * 0.15;
			} else if (activePowerFactor >= 0.95 && activePowerFactor <= 1) {// 减少电费
				return -0.75;
			}
			return 0.00;

		}
	}

	/**
	 * 0.85标准
	 * 
	 * @author yanlin.chen
	 * @email yanlin.chen@edianniu.com
	 * @date 2017年12月28日 下午5:45:05
	 * @version V1.0
	 */
	static class ChargeAdjustmentTable_85 extends ChargeAdjustmentTable {

		public ChargeAdjustmentTable_85() {
			super(0.85);
		}

		@Override
		protected Double getAdjustmentRate(Double activePowerFactor) {
			if (activePowerFactor <= 0.59) {
				return ((super.standardPowerFactor - activePowerFactor) / 0.01) * 2;// 增加电费
			} else if (activePowerFactor >= 0.60 && activePowerFactor <= 0.64) {
				return (0.65 - activePowerFactor) * 100 * 1 + 10;// 增加电费
			} else if (activePowerFactor < super.standardPowerFactor
					&& activePowerFactor >= 0.65) {
				return (super.standardPowerFactor - activePowerFactor) * 100 * 0.5;// 增加电费
			} else if (activePowerFactor >= super.standardPowerFactor
					&& activePowerFactor <= 0.90) {// 减少电费
				return -(activePowerFactor - super.standardPowerFactor) * 100 * 0.1;
			} else if (activePowerFactor == 0.91) {// 减少电费
				return -0.65;
			} else if (activePowerFactor == 0.92) {// 减少电费
				return -0.80;
			} else if (activePowerFactor == 0.92) {// 减少电费
				return -0.95;
			} else if (activePowerFactor >= 0.94 && activePowerFactor <= 1) {// 减少电费
				return -1.10;
			}
			return 0.00;

		}

	}
	/**
	 * 0.80标准
	 * 
	 * @author yanlin.chen
	 * @email yanlin.chen@edianniu.com
	 * @date 2017年12月28日 下午5:45:05
	 * @version V1.0
	 */
	static class ChargeAdjustmentTable_80 extends ChargeAdjustmentTable {

		public ChargeAdjustmentTable_80() {
			super(0.80);
		}

		@Override
		protected Double getAdjustmentRate(Double activePowerFactor) {
			if (activePowerFactor <= 0.54) {
				return ((super.standardPowerFactor - activePowerFactor) / 0.01) * 2;// 增加电费
			} else if (activePowerFactor >= 0.59 && activePowerFactor <= 0.55) {
				return (0.65 - activePowerFactor) * 100 * 1 + 10;// 增加电费
			} else if (activePowerFactor < super.standardPowerFactor
					&& activePowerFactor >= 0.60) {
				return (super.standardPowerFactor - activePowerFactor) * 100 * 0.5;// 增加电费
			} else if (activePowerFactor >= super.standardPowerFactor
					&& activePowerFactor <= 0.90) {// 减少电费
				return -(activePowerFactor - super.standardPowerFactor) * 100 * 0.1;
			} else if (activePowerFactor == 0.91) {// 减少电费
				return -1.15;
			} else if (activePowerFactor >= 0.92 && activePowerFactor <= 1) {// 减少电费
				return -1.30;
			}
			return 0.00;

		}

	}

}
