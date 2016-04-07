INSERT INTO mydb.groups
(groupname, description,address,latitude,longitude) VALUES
('Powerball','card game','1385 Woodroffe Ave, Ottawa, ON K2G 1V8', 45.349394, -75.758364),
('DragonRiders','table top RPG', '228 Bank St, Ottawa, ON K2P 1X1',45.4168546,-75.6978038),
('The Dudes','FPS shooter','75 Laurier Ave E, Ottawa, ON K1N 6N5',45.423433, -75.685458),
('Happy Feet','DDR party','1077 Bank St, Ottawa, ON K1S 3W9',45.3945333, -75.6834339),
('Dungeon Raiders','WOW raid party','223 Bank St, Ottawa, ON K2P 1W9',45.4169935,-75.6977367),
('The Sith Lords','Star Wars battlefront','1125 Colonel By Dr, Ottawa, ON K1S 5B6',45.383750,-75.697496);

INSERT INTO mydb.users
(username,password,email) VALUES
('Homer','FgxlLug8pYg=cGFzc3dvcmQ=','9iZyn19UwhI=c3ByaW5nRmllbGQxQG5vd2hlcmUuY29t'),  /*password*/
('Bart','/09QFwuAjEQ=Z3Vlc3NpdA==','suOTgYV2z40=c3ByaW5nRmllbGQyQG5vd2hlcmUuY29t'),   /*guessit*/
('Marge','B7gfWajwdzs=aG9taWU=','i8G13iqNsdk=c3ByaW5nRmllbGQzQG5vd2hlcmUuY29t'),     /*homie*/
('Maggie', 'o3EoZQD4DjY=b2htbm9t','xFpQSdkETDc=c3ByaW5nRmllbGQ0QG5vd2hlcmUuY29t'), /*ohmnom*/
('Lisa','Y2EUQRVN+jM=c2F4cGhvbmU=','07bMP+RMZ7s=c3ByaW5nRmllbGQ1QG5vd2hlcmUuY29t'), /*saxphone*/
('Soap', '9SZTiJp4nkA=U1NERA==','cDngGdodm5s=bG9ja2FuZGxvYWRAc2FzLmNvbQ=='),         /*SSDD*/
('Price','nK1omx94n8Q=Y2lnYXI=','YB1EcetAYvA=ZmlyZWF0d2lsbEBzYXMuY29t'),        /*cigar*/
('Luke','7z132aNpeqo=dGhlZm9yY2U=','GfVOpIn9nKw=c2t5d2Fsa2VyMkBqZWRpcGxhY2UuY29t'), /*theforce*/
('Yoda','7EYeXeRw3Es=dXNldGhlZm9yY2V5b3VtdXN0','b45cGwWVVIE=dGhlbWFzdGVyQGplZGlwbGFjZS5jb20='), /*usetheforceyoumust*/
('House','k1chxzAqOBg=dGhlZG9jaXNpbg==','5EubcPzfGOE=ZWxlbWVudHJ5QGhvc3BpdGFsLmNvbQ=='),  /*thedocisin*/
('Bill','rNfwfXmK6SU=aWx1dndpbmRvd3M=','XN3u6MhHSEQ=dGhlcmljaGVzdEBtcy5jb20='),  /*iluvwindows*/
('Android','ZEUhvgQTah0=YXBwbGVpc3Rhc3R5','QgsxzmCGb2k=ZXZlcnl3aGVyZUBhbGxwbGFjZS5jb20=');  /*appleistasty*/

INSERT INTO mydb.users_groups
(user_id,group_id,is_leader)VALUES
('1','6','1'), /*Homer is the leader for group 6*/
('1','3','0'),
('2','3','1'), /*Bart is the leader for group 3*/
('3','1','1'), /*Marge is the leader for group 1*/
('4','4','1'), /*Maggie is the leader for group 4*/
('5','5','1'), /*Lisa is the leader for group 5*/
('6','3','0'), /*Soap is a member of group 3*/
('7','2','1'), /*Price is the leader for group 2*/
('8','6','0'), /*Luke is a member of group 6*/
('9','4','0'), /*Yoda is a member of group 4*/
('10','2','0'), /*House is a member of group2*/
('11','1','0'), /*Bill is a member of group1*/
('12','5','0'); /*Android is a member of group5*/

INSERT INTO mydb.conversation (user_start_id, user_recieve_id, status) VALUES
(1, 3, 'ACTIVE');

INSERT INTO mydb.conversation_message (message, time_stamp, user_id_fk, conversation_id_fk) VALUES
('Marge, where are you?', NOW(), 1, 1),
('Right here Homie, on the website', NOW(), 3, 1),
('But I cant see you', NOW(), 1, 1),
('Of course you cant were on the internet', NOW(), 3, 1),
('This internet stuff is hard', NOW(), 1, 1),
('No, youre just dumb Homer', NOW(), 3, 1);

INSERT INTO mydb.tags (tag_name) VALUES 
('mtg'), 
('pathfinder'),
('cs:go'),
('ddr'),
('wow'),
('starwars');

insert into mydb.groups_tags(group_id_fk,tag_id_fk) values 
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6);

