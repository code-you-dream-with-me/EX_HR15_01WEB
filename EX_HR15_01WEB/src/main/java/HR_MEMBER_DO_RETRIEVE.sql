--PAGING,1~10,�ѰǼ�
SELECT A.*,B.*
FROM(
    SELECT t2.rnum,
           t2.u_id,
           t2.name,
           t2.u_level,
           t2.login,
           t2.recommend,
           t2.email,
           --����:�ú�,�׷�ġ ������ �����
           DECODE(TO_CHAR(SYSDATE,'YYYYMMDD'),TO_CHAR(t2.reg_dt,'YYYYMMDD')
                  ,TO_CHAR(t2.reg_dt,'HH24:MI')
                  ,TO_CHAR(t2.reg_dt,'YYYY/MM/DD')) reg_dt
    FROM(
        SELECT ROWNUM rnum,t1.*
        FROM (
            SELECT *
            FROM hr_member
            --WHERE����
            ORDER BY reg_dt desc
        )t1
    )t2 
    --&PAGE_SIZE=10,&PAGE_NUM=1
   -- WHERE rnum BETWEEN (&PAGE_SIZE * (&PAGE_NUM-1)+1) AND (&PAGE_SIZE * (&PAGE_NUM-1)+&PAGE_SIZE)
    WHERE rnum BETWEEN 1 AND 10
)A
CROSS JOIN
    (SELECT COUNT(*) total_cnt
     FROM hr_member
    --WHERE����: �̸�(10),id(20),EMAIL(30)
    )B
;



