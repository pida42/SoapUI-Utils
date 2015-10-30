/*'The Decidotron' takes a total number of requests you wish to run,
 * and a percentage split between the 4 billing options. Welcome
 * everyone, to 'The Decidotron'
 */

//Get the relevant properties from the test
def tot_requests = context.expand( '${#TestCase#tot_requests}' );
def billPercent = context.expand( '${#TestCase#bill}' );
def nobillPercent = context.expand( '${#TestCase#nobill}' );
def trialPercent = context.expand( '${#TestCase#trial}' );
def prepayPercent = context.expand( '${#TestCase#prepay}' );
def ccbillPercent = context.expand( '${#TestCase#ccbill}' )


//Create our multiplication value for percentage calculations
def multiValue = tot_requests.toInteger()/100;
//log.info(multiValue)

//Process our percentages to number of requests
def bill = Math.round(billPercent.toInteger()*multiValue);
//log.info(bill)
def nobill = Math.round(nobillPercent.toInteger()*multiValue);
//log.info(nobill)
def trial = Math.round(trialPercent.toInteger()*multiValue);
//log.info(trial)
def prepay = Math.round(prepayPercent.toInteger()*multiValue);
//log.info(prepay)
def ccbill = Math.round(ccbillPercent.toInteger()*multiValue);

//Iterate through our checks based on the number of requests each
Date startDate = new Date();
log.info('Started at '+startDate.toString());
bill.times{
	testRunner.runTestStepByName('bill')
	sleep(500)
	};
nobill.times{
	testRunner.runTestStepByName('nobill')
	sleep(500)
	};
trial.times{
	testRunner.runTestStepByName('trial')
	sleep(500)
	};
prepay.times{
	testRunner.runTestStepByName('prepay')
	sleep(500)
	};
ccbill.times{
	testRunner.runTestStepByName('ccbill')
	sleep(500)
	};
Date endDate = new Date();
log.info('Ended at '+endDate.toString());

 