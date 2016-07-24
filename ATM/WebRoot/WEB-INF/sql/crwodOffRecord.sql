CREATE PROCEDURE `atm`.`<crowdOffRecord>`(
IN userID varchar(15)
) 
BEGIN
	SELECT cr.crowdID, cr.sendContent,cr.sendTime
  from chatRecord cr,userInfo u,crowdMenber cm
  WHERE userID=u.userID and cm.userID=u.userID and cr.crowdID=cm.crowdID
		and cr.sendTime>=NOW()-u.offTime and cr.sendTime<=NOW();
	ORDER BY cr.sendTime DESC;
END
