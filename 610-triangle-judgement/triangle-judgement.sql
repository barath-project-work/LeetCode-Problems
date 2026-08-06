# Write your MySQL query statement below
SELECT *,
    CASE
        WHEN x + y > z
         AND x + z > y
         AND z + y > x
        THEN 'Yes'
        ELSE 'No'
    END AS triangle
FROM Triangle;