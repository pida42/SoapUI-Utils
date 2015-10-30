import groovy.time.TimeCategory

def today = new Date()

use(TimeCategory) {
	
def currentDate =  today.format('yyyy-MM')+'-01'
testRunner.testCase.setPropertyValue('CurrentDate', currentDate)

def DateBefore1Month = today - 1.months;
testRunner.testCase.setPropertyValue('DateBefore1Month', DateBefore1Month.format('yyyy-MM')+'-01')

def NextMonthDate = today + 1.month;
testRunner.testCase.setPropertyValue('NextMonthDate', NextMonthDate.format('yyyy-MM')+'-01')

def DateBefore2Months = today - 2.months;
testRunner.testCase.setPropertyValue('DateBefore2Months', DateBefore2Months.format('yyyy-MM')+'-01')


def tempDate = DateBefore1Month.format('yyyy-MM')+'-01'
tempDate = new Date().parse("yyyy-MM-dd", tempDate)
def DateBefore2MonthsLastMonthDay = tempDate - 1.day;
log.info DateBefore2MonthsLastMonthDay
testRunner.testCase.setPropertyValue('DateBefore2MonthsLastMonthDay', DateBefore2MonthsLastMonthDay.format('yyyy-MM-dd'))

}