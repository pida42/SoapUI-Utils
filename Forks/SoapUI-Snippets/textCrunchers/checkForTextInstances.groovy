//Get our log file location
def logFileLocation = context.expand( '${#TestSuite#logFileLocation}' )

//Get a new file object with our log file
BufferedReader br;
try
{
	br = new BufferedReader(new FileReader(new File(logFileLocation)));
	log.info("Reading ${logFileLocation} has been a success!")
} catch (Exception e){
	log.info(e)
}

//Read in line by line and log to a variable
def line = '';
def lineCount = 0;
def logLinesList = [];
while(true){
	line = br.readLine();
	if(line.equals(null)) 
		break;
	logLinesList << line;
	lineCount++;
}

line = null;

br.close();

//Get the total number of lines
log.info('The total number of log lines is: '+(logLinesList.size()));

//Read in the file of source IP's we are monitoring
def sourceIPLocation = context.expand( '${#TestSuite#sourceIPLocation}' )

//Read in the new file
BufferedReader br1;
try
{
	br1 = new BufferedReader(new FileReader(new File(sourceIPLocation)));
	log.info("Reading ${sourceIPLocation} has been a success!")
} catch (Exception e){
	log.info(e)
}

//Prepare an object to write to a results file
def resultsFile = context.expand( '${#TestSuite#resultsFile}' )
File file;
PrintWriter pw;

try
{
	file = new File(resultsFile);
	pw = new PrintWriter(new FileWriter(file), true);
	pw.println('******IP Instances in Log File******');
} catch (IOException o){
	log.info(o)
}

//Read in to a list
def sourceIPs = [];
while(true){
	line = br1.readLine();
	if(line.equals(null)) 
		break;
	sourceIPs << line;
}

br1.close();

log.info(sourceIPs)

def countIP = 0;
for(sourceIP in sourceIPs){
	//log.info(sourceIP)
	for(logLine in logLinesList){
		line = logLine.toString();
		//log.info(logLine)
		if(line.contains(sourceIP)){
			countIP++
		}
		
	}
	log.info(sourceIP+': '+(countIP));
	//Write each number of IP instances to a results file
	pw.println(sourceIP+': '+(countIP));
	countIP = 0
}

pw.flush();
pw.close();

