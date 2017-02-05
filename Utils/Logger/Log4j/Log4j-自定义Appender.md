## 自定义输出Appender

Log4j支持自定义的输出，需要继承自org.apache.log4j.AppenderSkeleton抽象类，并实现append(LoggingEvent event)，close()，requiresLayout()方法，示例如下：

	package com.wangdh.learner.logger.log4j.customer;
	
	import org.apache.log4j.AppenderSkeleton;
	import org.apache.log4j.spi.ErrorCode;
	import org.apache.log4j.spi.LoggingEvent;
	
	public class CountingConsoleAppender extends AppenderSkeleton {
		int counter = 0;
		int limit = 16;
	
		public int getLimit() {
			return limit;
		}
	
		public void setLimit(int limit) {
			this.limit = limit;
		}
	
		/**
		 * 关掉
		 */
		@Override
		public void close() {
			if (this.closed) {
				return;
			}
	
			this.closed = true;
		}
	
		/**
		 * 需要使用布局
		 */
		@Override
		public boolean requiresLayout() {
	
			return true;
		}
	
		/**
		 * 日志记录
		 */
		@Override
		protected void append(LoggingEvent event) {
			if (this.layout == null) {
				errorHandler.error("没有设置[" + name + "]输出布局.", null, ErrorCode.MISSING_LAYOUT);
	
				return;
			}
	
			if (counter >= limit) {
				errorHandler.error("输出次数[" + limit + "]达到了极限.", null, ErrorCode.WRITE_FAILURE);
	
				return;
			}
	
			System.out.print(this.layout.format(event));
			if (layout.ignoresThrowable()) {
				String[] t = event.getThrowableStrRep();
				if (t != null) {
					for (String string : t) {
						System.out.println(string);
					}
				}
			}
			counter++;
		}
	}

相关配置：

	### 配置Logger ###
	log4j.logger.com.wangdh.learner.logger.Log4jTest = DEBUG,custom
	
	### 自定义Appender ###
	log4j.appender.custom = com.wangdh.learner.logger.log4j.customer.CountingConsoleAppender
	### 最多输出20次 ###
	log4j.appender.custom.limit = 20
	log4j.appender.custom.layout = org.apache.log4j.PatternLayout
	log4j.appender.custom.layout.ConversionPattern = [%t] [%-5p] %d{yyyy-MM-dd HH:mm:ss,SSS} [%l] [%m]%n
