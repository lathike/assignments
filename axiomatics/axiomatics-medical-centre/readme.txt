BUILDING
-------------
mvn clean install
java -jar target/axiomatics-medical-centre-1.0-SNAPSHOT.jar


USAGE
-------------
All endpoints can be accessed via Postman. In the Authorization tab set the authentication type to "Basic Auth" and specify the username and passwords below.

To log in use any of the users specified in import.sql
ex:
username: 198001010110
password: password


ENDPOINTS
-------------
http://localhost:8090/radiographs/ofpatient?patient=198001011001
http://localhost:8090/radiographs/requestedby?doctor=198001010110
http://localhost:8090/radiographs/performedby?radiologist=198001011001
http://localhost:8090/staff/doctors/all
http://localhost:8090/patients/198001011001

To create a radiograph:
POST http://localhost:8090/radiographs/create

Sample json body:
{"patient":{"id": 1},"requestedBy":{"id":1},"reason": "i aint got no satisfaction"}


