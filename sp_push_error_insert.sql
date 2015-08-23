DROP PROCEDURE IF EXISTS sp_push_error_insert;
CREATE PROCEDURE `sp_push_error_insert`(IN device_token VARCHAR(255), IN error_msg VARCHAR(255))
BEGIN

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
 
    ROLLBACK;
    SELECT 'Failed' AS error_msg ;
  END;
 
  START TRANSACTION;

    INSERT INTO bm_push_error (device_token, error_msg, reg_date )
    VALUES (device_token , error_msg, NOW());
 
  COMMIT;
      
END;
