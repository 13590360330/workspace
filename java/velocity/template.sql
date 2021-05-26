--基于模板生成sql代码

insert overwrite table $targettable
select
$selectCondition
from $fromCondition
where $whereCondition;


