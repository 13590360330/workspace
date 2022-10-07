SELECT
  T1.ID AS ID,
  T1.COLUMN1 AS COL1,
  T2.COLUMN1 AS COL2,
  T3.COLUMN3 AS COL3,
  (select count(*) from TAB2) as count1,
  T4.col5 AS COL5,
  DISTINCT(T3.COLUMN4) AS COL4
FROM
    TAB1 T1
LEFT JOIN TAB2 T2 ON T1.ID=T2.ID
LEFT JOIN TAB3 T3 ON T2.ID=T3.ID
LEFT JOIN ( select t1.column5 as col5 from tab4 t1) T4 ON T4.ID=T3.ID
WHERE
    T1.ID=1
GROUP BY
    T1.COLUMN1,T2.COLUMN1,T3.COLUMN3
HAVING
    T1.COLUMN1 <> "2"
ORDER BY
    T1.ID
LIMIT
    10