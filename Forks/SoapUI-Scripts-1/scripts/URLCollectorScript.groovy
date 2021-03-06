/*</pre>
@Author : Diganth Aswath
@Description : Collects URLs from "CreateIndicium" responses and stores the result in a specific folder.
@GroovyTestStepName : "Groovy Script - Print URL to file"
*/
import com.eviware.soapui.impl.wsdl.teststeps.*
import com.eviware.soapui.support.types.StringToStringMap 

//Function to replace https with http in URL to enable capture of label images.
def modURLString(url){
	url.replaceFirst(/^https/, "http")
}

//Function to create a Text File with TestStep name
def file_create (testStepname, type, url, arg1){
	def fileName = dirName+testStepname+'_'+arg1+type
	// Write output to file created
	writetoFile (fileName, url)
}

// Function to write responseURL to the file.
def writetoFile (fileName,  url){
	//def file = new File(fileName)
	//file.write(url, "UTF-8") //Writing response into the file created
	modded_url = modURLString(url)
	def URLimgfile = new FileOutputStream(fileName)
	def out = new BufferedOutputStream(URLimgfile)
	//log.info new URL(url).openStream()
	try{
		out << new URL(url).openStream()
	} catch (e){ 
		out << new URL(modded_url).openStream()
	}
	out.close()
}

// Function that controls the logic of iterating through the testSteps to obtain
// URL from the response.
def printURL (arg1) { 
	logFile << "${today}:: ARGUMENT :: ${arg1}" <<"\r\n"
	def testSteps = myTestCase.getTestStepList()
	testSteps.each {
		// Checking if TestStep is of WSDLTestRequest type
		if (it instanceof com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep){
			// Reading Raw request to extract Namespace to use.
			def rawRequest = it.getProperty("Request").getValue()
			if (rawRequest.contains("CreateIndicium")){ 
				String[] nameSpaceURL= rawRequest.findAll('https?://[^\\s<>"]+|www\\.[^\\s<>"]+')
				// Reading response content into an object
				def url = context.expand( '${'+it.name+'#Response#declare namespace ns1=\''+nameSpaceURL[1]+'\';//ns1:CreateIndiciumResponse[1]/ns1:URL[1]}')
				// Checking if URL string is empty
				if(url?.trim()) {
					logFile << "${today}::INFO::Captured label for -> ${it.name} -> ${arg1}" <<"\r\n"
					if(url.contains(".pdf")) {
						file_create (it.name, ".pdf", url, arg1)
					}
					else if (url.contains(".png")){
						file_create (it.name, ".png", url, arg1)
					}
                                        else if (url.contains(".zpl")){
                                            file_create (it.name, ".zpl", url, arg1)
                                        }
				}	
				else {
					logFile << "${today}::ERROR::Unable to capture label for -> ${it.name} ->${arg1}" <<"\r\n"
				}
		 }
		}
	}
}

today = new Date()
todayDate = today.getDateString().split('/').join('_')
todayTime = today.getTimeString().split(':').join('_')
myTestCase = context.testCase
myTestCaseName = myTestCase.name
myProjectName = myTestCase.testSuite.project.name
filePath = "C:/Users/daswath/Copy/QA_Documents/SoapUI Projects/Result/"
dirName = filePath+myProjectName+'_'+myTestCaseName+'_'+todayDate+'_'+todayTime+'/'
File1 = new File(dirName).mkdirs()
logFile = new File(dirName+"/Log.log")
printURL(arg1) // Print response URLs.

 