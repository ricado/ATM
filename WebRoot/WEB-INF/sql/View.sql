CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER 
VIEW `<crowdRecordView>` 
AS 
select `c`.`recordID` AS `recordID`,`c`.`crowdID` AS `crowdID`,
`cc`.`crowdName` AS `crowdName`,`c`.`sendContent` AS `sendContent`,
`c`.`sendTime` AS `sendTime`,`u`.`userID` AS `userID`,`u`.`nickname` 
AS `nickname`,`u`.`headImagePath` AS `headImagePath` 
from ((`chatRecord` `c` join `userInfo` `u`) join `crowdChat` `cc`) 
where ((`c`.`userID` = `u`.`userID`) and (`c`.`crowdID` = `cc`.`crowdID`))
