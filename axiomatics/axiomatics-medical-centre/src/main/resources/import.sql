INSERT INTO MEDICALSPECILIZATIONS(id,name) VALUES(1, 'Allergy and immunology');
INSERT INTO MEDICALSPECILIZATIONS(id,name) VALUES(2, 'Anaesthesiology');
INSERT INTO MEDICALSPECILIZATIONS(id,name) VALUES(3, 'Pathology');
INSERT INTO MEDICALSPECILIZATIONS(id,name) VALUES(4, 'Cardiology');

INSERT INTO STAFF(type, id, firstname, lastname, socialsecuritynumber, password, isactive) VALUES('D',1,'Vincent','Strange','198001010110','$2a$10$imBBHKb73a5NO6ihS786Ie8/qd.2eOXlDEBzZxaQxyzkaFE5LGcoW', TRUE);
INSERT INTO STAFF(type, id, firstname, lastname, socialsecuritynumber, password, isactive) VALUES('D',2,'Otto','Octavius','198002010220','$2a$10$imBBHKb73a5NO6ihS786Ie8/qd.2eOXlDEBzZxaQxyzkaFE5LGcoW', TRUE);
INSERT INTO STAFF(type, id, firstname, lastname, socialsecuritynumber, password, isactive) VALUES('D',3,'Charles','Xavier','198003010330','$2a$10$imBBHKb73a5NO6ihS786Ie8/qd.2eOXlDEBzZxaQxyzkaFE5LGcoW', TRUE);

INSERT INTO DOCTORS(id, specializationid) VALUES(1, 1);
INSERT INTO DOCTORS(id, specializationid) VALUES(2, 3);
INSERT INTO DOCTORS(id, specializationid) VALUES(3, 4);

INSERT INTO PATIENTS(id, socialSecurityNumber, firstName, lastName, doctorid) values(1,'198001011001','John','Doe',1);
INSERT INTO PATIENTS(id, socialSecurityNumber, firstName, lastName, doctorid) values(2,'198002012002','Jane','Doe',null);

INSERT INTO RADIOGRAPHS(id, patientid, performedbyradiologistid,requesteddoctorsid, requestedOn, performedOn, diagnosis, reason) VALUES (1,1,null,1,'2016-08-01 10:15:00', null, '','chest pains');
INSERT INTO RADIOGRAPHS(id, patientid, performedbyradiologistid,requesteddoctorsid, requestedOn, performedOn, diagnosis, reason) VALUES (2,1,null,1,'2016-09-01 10:15:00', null, '','follow up on lung surgery');