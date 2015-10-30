//Establish a connection
import groovy.sql.Sql

def db = [url: "jdbc:sqlserver://PLLPC6229:3711;databaseName=dev;IntegratedSecurity=true", driver:"com.microsoft.sqlserver.jdbc.SQLServerDriver"];
def sql = Sql.newInstance(db.url,db.driver);

//Get a list of tables
def checksTables = '';
sql.eachRow("Select sysobjects.name As sysobjects_name From sysobjects Where sysobjects.name Like 'checks%' and sysobjects.name Not Like 'checks_out' And sysobjects.type = 'U'") {
	def stringRep = it.toString();
     def groupId = stringRep.substring(23);
     def finalGroupId = groupId.replace("]","");
     checksTables = checksTables+', '+finalGroupId;
}

checksTables = checksTables.substring(1);

//Get a list to exclude
def exclusions = [
'[groupName:ExcludeMe]', 

]

//Generic string to faff with
def stringRep = '';

def doNotPublishList = [];

sql.eachRow('SELECT groupName FROM groups WHERE groupId NOT IN ('+checksTables+')') {
		stringRep = it.toString();
		for(excl in exclusions){
				if(excl.contains(stringRep)){
					doNotPublishList << stringRep
				}
			}	
		}
def publishList = [];
sql.eachRow('SELECT groupName FROM groups WHERE groupId NOT IN ('+checksTables+')') {
		stringRep = it.toString();
		publishList << stringRep
		for(dnp in doNotPublishList){
			publishList -= dnp	
		}
	}	

publishList.each{
	log.info('These are included: '+it)
}

