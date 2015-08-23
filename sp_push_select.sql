DROP PROCEDURE IF EXISTS sp_push_select;
CREATE PROCEDURE `sp_push_select`(IN svc_id varchar(5))
BEGIN

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
 
    ROLLBACK;
    SELECT 'Failed' AS error_msg ;
  END;


  START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_ap;
    CREATE TEMPORARY TABLE `temp_ap` (
      `seq` int(10) unsigned NOT NULL ,
      `svc_id` varchar(4) NOT NULL,
      `device_token` varchar(500) NOT NULL ,
      `message` varchar(255) NOT NULL,
      `link` varchar(255) NOT NULL,
      `alarm_id` varchar(10) NOT NULL ,
      `reg_date` datetime NOT NULL ,
      KEY `temp_ap` (`seq`)
    ) ENGINE=MEMORY DEFAULT CHARSET= utf8;


    INSERT INTO temp_ap
    SELECT seq, svc_id, device_token, message, link, alarm_id, reg_date
      FROM bm_push_pool AS A
     WHERE A.send_date IS NULL
       AND A.svc_id = svc_id
     LIMIT 10;


    IF EXISTS (SELECT * FROM temp_ap) THEN
       
      UPDATE bm_push_pool A
       INNER JOIN temp_ap B ON A.seq = B.seq
         SET A.send_date = NOW ();
     
    END IF;

  COMMIT;
 
  SELECT * FROM temp_ap ;
  drop table temp_ap ; 
      
END;
