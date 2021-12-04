# 数据库设计文档

**数据库名：** test

**文档版本：** 1.0.0

**文档描述：** 数据库设计文档生成
| 表名                  | 说明       |
| :-------------------- | :--------- |
| [a_test_2021-11-23](#a_test_2021-11-23) |  |
| [a_test_2021-11-24](#a_test_2021-11-24) |  |
| [branch_table](#branch_table) |  |
| [buy_noble_record](#buy_noble_record) |  |
| [doooly_members](#doooly_members) | 兜礼会员表 |
| [employee](#employee) |  |
| [global_table](#global_table) |  |
| [hjmall_area](#hjmall_area) |  |
| [interface_info](#interface_info) | Dataway中的API |
| [interface_release](#interface_release) | DatawayAPI发布历史。 |
| [internal_shop_consumption_quota](#internal_shop_consumption_quota) | 内购用户消费额度记录表 |
| [internal_shop_product_purchase](#internal_shop_product_purchase) | 内购用户消费额度记录表 |
| [mall_product_storage_copy1](#mall_product_storage_copy1) | 商品仓库 |
| [ms_category](#ms_category) |  |
| [ms_page_template](#ms_page_template) |  |
| [ms_template_detail](#ms_template_detail) |  |
| [my_access_log](#my_access_log) |  |
| [person](#person) |  |
| [prd_sku_storage](#prd_sku_storage) | SKU库存 |
| [tb_user](#tb_user) |  |
| [test](#test) |  |
| [test_binlog](#test_binlog) |  |
| [test_import](#test_import) |  |
| [test_to_es](#test_to_es) |  |
| [ts_product_delete_log](#ts_product_delete_log) | 商品删除操作记录表 |
| [users](#users) |  |
**表名：** <a id="a_test_2021-11-23">a_test_2021-11-23</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | code |   varchar   | 50 |   0    |    Y     |  N   |       |   |
|  4   | shipping_fee |   int   | 10 |   0    |    Y     |  N   |   1    | 订单原始运费金额  |
|  5   | shipping_fee_original |   int   | 10 |   0    |    Y     |  N   |       | 订单原始运费金额  |
|  6   | CUSTOMER_ESTATE_FLAG |   varchar   | 20 |   0    |    N     |  N   |   N    | 地产业主标识  |
|  7   | CHECK_FLAG |   varchar   | 5 |   0    |    Y     |  N   |       | 核算是否正确  |
|  8   | CHECK_AMOUNT |   decimal   | 20 |   5    |    Y     |  N   |       | 核算欠费金额  |
|  9   | is_chain |   int   | 10 |   0    |    Y     |  N   |   1    | is_chain是否是门店，is_chain=0是大仓，is_chain=1：是门店  |
|  10   | order_type |   tinyint   | 3 |   0    |    Y     |  N   |       | 订单类型1普通订单(默认),2闪送订单,3门店自提订单，4处方订单，5处方闪送，6处方门店自提7:积分商城订单10:同城配送订单  |
|  11   | sales_num |   int   | 10 |   0    |    Y     |  N   |   0    | 销售数量  |
|  12   | eva_num |   int   | 10 |   0    |    Y     |  N   |   0    | 评论数量  |
|  13   | store_type |   int   | 10 |   0    |    Y     |  N   |       | 门店类型：0oto门店，1b2c门店  |
|  14   | org_id |   int   | 10 |   0    |    Y     |  N   |       | 组织机构id  |
|  15   | sent_support |   int   | 10 |   0    |    Y     |  N   |       | 是否支持无货发货：0不支持，1支持  |
**表名：** <a id="a_test_2021-11-24">a_test_2021-11-24</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | code |   varchar   | 50 |   0    |    Y     |  N   |       |   |
|  4   | shipping_fee |   int   | 10 |   0    |    Y     |  N   |   1    | 订单原始运费金额  |
|  5   | shipping_fee_original |   int   | 10 |   0    |    Y     |  N   |       | 订单原始运费金额  |
|  6   | CUSTOMER_ESTATE_FLAG |   varchar   | 20 |   0    |    N     |  N   |   N    | 地产业主标识  |
|  7   | CHECK_FLAG |   varchar   | 5 |   0    |    Y     |  N   |       | 核算是否正确  |
|  8   | CHECK_AMOUNT |   decimal   | 20 |   5    |    Y     |  N   |       | 核算欠费金额  |
|  9   | is_chain |   int   | 10 |   0    |    Y     |  N   |   1    | is_chain是否是门店，is_chain=0是大仓，is_chain=1：是门店  |
|  10   | order_type |   tinyint   | 3 |   0    |    Y     |  N   |       | 订单类型1普通订单(默认),2闪送订单,3门店自提订单，4处方订单，5处方闪送，6处方门店自提7:积分商城订单10:同城配送订单  |
|  11   | sales_num |   int   | 10 |   0    |    Y     |  N   |   0    | 销售数量  |
|  12   | eva_num |   int   | 10 |   0    |    Y     |  N   |   0    | 评论数量  |
|  13   | store_type |   int   | 10 |   0    |    Y     |  N   |       | 门店类型：0oto门店，1b2c门店  |
|  14   | org_id |   int   | 10 |   0    |    Y     |  N   |       | 组织机构id  |
|  15   | sent_support |   int   | 10 |   0    |    Y     |  N   |       | 是否支持无货发货：0不支持，1支持  |
**表名：** <a id="branch_table">branch_table</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | branch_id |   bigint   | 19 |   0    |    N     |  Y   |       |   |
|  2   | xid |   varchar   | 128 |   0    |    N     |  N   |       |   |
|  3   | transaction_id |   bigint   | 19 |   0    |    Y     |  N   |       |   |
|  4   | resource_group_id |   varchar   | 32 |   0    |    Y     |  N   |       |   |
|  5   | resource_id |   varchar   | 256 |   0    |    Y     |  N   |       |   |
|  6   | lock_key |   varchar   | 128 |   0    |    Y     |  N   |       |   |
|  7   | branch_type |   varchar   | 8 |   0    |    Y     |  N   |       |   |
|  8   | status |   tinyint   | 3 |   0    |    Y     |  N   |       |   |
|  9   | client_id |   varchar   | 64 |   0    |    Y     |  N   |       |   |
|  10   | application_data |   varchar   | 2000 |   0    |    Y     |  N   |       |   |
|  11   | gmt_create |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  12   | gmt_modified |   datetime   | 19 |   0    |    Y     |  N   |       |   |
**表名：** <a id="buy_noble_record">buy_noble_record</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | noble_id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | user_id |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | active |   int   | 10 |   0    |    Y     |  N   |       |   |
|  4   | start_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  5   | end_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
**表名：** <a id="doooly_members">doooly_members</a>

**说明：** 兜礼会员表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 主键id  |
|  2   | user_id |   varchar   | 64 |   0    |    N     |  N   |       | 国大会员ID  |
|  3   | d_card_number |   varchar   | 20 |   0    |    N     |  N   |       | 兜礼会员卡号  |
|  4   | d_telephone |   varchar   | 13 |   0    |    N     |  N   |       | 兜礼会员手机号码  |
|  5   | d_type |   varchar   | 10 |   0    |    N     |  N   |       | 兜礼会员状态【0：员工1：家属2：退休3：离职】  |
|  6   | d_active_date |   datetime   | 19 |   0    |    N     |  N   |       | 兜礼会员激活时间（格式：yyyy-MM-ddHH:mm:ss.s）  |
|  7   | creation_date |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  8   | last_updated_date |   datetime   | 19 |   0    |    N     |  N   |       | 最后更新时间  |
|  9   | attribute1 |   varchar   | 30 |   0    |    Y     |  N   |       | 扩展字段1  |
|  10   | attribute2 |   varchar   | 30 |   0    |    Y     |  N   |       | 扩展字段2  |
**表名：** <a id="employee">employee</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | lastName |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | email |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  4   | gender |   int   | 10 |   0    |    Y     |  N   |       |   |
|  5   | d_id |   int   | 10 |   0    |    Y     |  N   |       |   |
**表名：** <a id="global_table">global_table</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | xid |   varchar   | 128 |   0    |    N     |  Y   |       |   |
|  2   | transaction_id |   bigint   | 19 |   0    |    Y     |  N   |       |   |
|  3   | status |   tinyint   | 3 |   0    |    N     |  N   |       |   |
|  4   | application_id |   varchar   | 32 |   0    |    Y     |  N   |       |   |
|  5   | transaction_service_group |   varchar   | 32 |   0    |    Y     |  N   |       |   |
|  6   | transaction_name |   varchar   | 128 |   0    |    Y     |  N   |       |   |
|  7   | timeout |   int   | 10 |   0    |    Y     |  N   |       |   |
|  8   | begin_time |   bigint   | 19 |   0    |    Y     |  N   |       |   |
|  9   | application_data |   varchar   | 2000 |   0    |    Y     |  N   |       |   |
|  10   | gmt_create |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  11   | gmt_modified |   datetime   | 19 |   0    |    Y     |  N   |       |   |
**表名：** <a id="hjmall_area">hjmall_area</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | area_code |   varchar   | 12 |   0    |    Y     |  N   |       |   |
|  2   | area_parent_code |   varchar   | 12 |   0    |    Y     |  N   |       |   |
|  3   | area_name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  4   | area_simple_name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  5   | area_level |   varchar   | 1 |   0    |    Y     |  N   |       |   |
|  6   | area_sort |   varchar   | 4 |   0    |    Y     |  N   |       |   |
**表名：** <a id="interface_info">interface_info</a>

**说明：** Dataway中的API

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | api_id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | api_method |   varchar   | 12 |   0    |    N     |  N   |       | HttpMethod：GET、PUT、POST  |
|  3   | api_path |   varchar   | 512 |   0    |    N     |  N   |       | 拦截路径  |
|  4   | api_status |   int   | 10 |   0    |    N     |  N   |       | 状态：0草稿，1发布，2有变更，3禁用  |
|  5   | api_comment |   varchar   | 255 |   0    |    Y     |  N   |       | 注释  |
|  6   | api_type |   varchar   | 24 |   0    |    N     |  N   |       | 脚本类型：SQL、DataQL  |
|  7   | api_script |   mediumtext   | 16777215 |   0    |    N     |  N   |       | 查询脚本：xxxxxxx  |
|  8   | api_schema |   mediumtext   | 16777215 |   0    |    Y     |  N   |       | 接口的请求/响应数据结构  |
|  9   | api_sample |   mediumtext   | 16777215 |   0    |    Y     |  N   |       | 请求/响应/请求头样本数据  |
|  10   | api_option |   mediumtext   | 16777215 |   0    |    Y     |  N   |       | 扩展配置信息  |
|  11   | api_create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  12   | api_gmt_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 修改时间  |
**表名：** <a id="interface_release">interface_release</a>

**说明：** DatawayAPI发布历史。

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | pub_id |   int   | 10 |   0    |    N     |  Y   |       | PublishID  |
|  2   | pub_api_id |   int   | 10 |   0    |    N     |  N   |       | 所属APIID  |
|  3   | pub_method |   varchar   | 12 |   0    |    N     |  N   |       | HttpMethod：GET、PUT、POST  |
|  4   | pub_path |   varchar   | 512 |   0    |    N     |  N   |       | 拦截路径  |
|  5   | pub_status |   int   | 10 |   0    |    N     |  N   |       | 状态：0有效，1无效（可能被下线）  |
|  6   | pub_type |   varchar   | 24 |   0    |    N     |  N   |       | 脚本类型：SQL、DataQL  |
|  7   | pub_script |   mediumtext   | 16777215 |   0    |    N     |  N   |       | 查询脚本：xxxxxxx  |
|  8   | pub_script_ori |   mediumtext   | 16777215 |   0    |    N     |  N   |       | 原始查询脚本，仅当类型为SQL时不同  |
|  9   | pub_schema |   mediumtext   | 16777215 |   0    |    Y     |  N   |       | 接口的请求/响应数据结构  |
|  10   | pub_sample |   mediumtext   | 16777215 |   0    |    Y     |  N   |       | 请求/响应/请求头样本数据  |
|  11   | pub_option |   mediumtext   | 16777215 |   0    |    Y     |  N   |       | 扩展配置信息  |
|  12   | pub_release_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 发布时间（下线不更新）  |
**表名：** <a id="internal_shop_consumption_quota">internal_shop_consumption_quota</a>

**说明：** 内购用户消费额度记录表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 主键id  |
|  2   | user_id |   varchar   | 64 |   0    |    N     |  N   |       | 用户ID  |
|  3   | store_id |   int   | 10 |   0    |    N     |  N   |       | 区域ID  |
|  4   | used_amount |   bigint   | 19 |   0    |    Y     |  N   |       | 已使用额度  |
|  5   | current_month |   date   | 10 |   0    |    N     |  N   |       | 当月额度  |
|  6   | creation_date |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  7   | last_updated_date |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 最后更新时间  |
|  8   | created_by |   bigint   | 19 |   0    |    Y     |  N   |   -1    | 创建人  |
|  9   | last_updated_by |   bigint   | 19 |   0    |    Y     |  N   |   -1    | 最后更新人  |
|  10   | attribute1 |   varchar   | 30 |   0    |    Y     |  N   |       | 扩展字段1  |
|  11   | attribute2 |   varchar   | 30 |   0    |    Y     |  N   |       | 扩展字段2  |
**表名：** <a id="internal_shop_product_purchase">internal_shop_product_purchase</a>

**说明：** 内购用户消费额度记录表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 主键id  |
|  2   | user_id |   varchar   | 64 |   0    |    N     |  N   |       | 用户ID  |
|  3   | store_id |   int   | 10 |   0    |    N     |  N   |       | 区域ID  |
|  4   | product_id |   int   | 10 |   0    |    Y     |  N   |       | 商品id  |
|  5   | product_code |   varchar   | 20 |   0    |    Y     |  N   |       | 商品编码  |
|  6   | sku_id |   bigint   | 19 |   0    |    Y     |  N   |       | 商品skuId  |
|  7   | purchase_quantity |   bigint   | 19 |   0    |    Y     |  N   |       | 已购买数量  |
|  8   | current_month |   date   | 10 |   0    |    N     |  N   |       | 当月额度  |
|  9   | creation_date |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  10   | last_updated_date |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 最后更新时间  |
|  11   | created_by |   bigint   | 19 |   0    |    Y     |  N   |   -1    | 创建人  |
|  12   | last_updated_by |   bigint   | 19 |   0    |    Y     |  N   |   -1    | 最后更新人  |
|  13   | attribute1 |   varchar   | 30 |   0    |    Y     |  N   |       | 扩展字段1  |
|  14   | attribute2 |   varchar   | 30 |   0    |    Y     |  N   |       | 扩展字段2  |
**表名：** <a id="mall_product_storage_copy1">mall_product_storage_copy1</a>

**说明：** 商品仓库

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   intunsigned   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | consignor |   varchar   | 225 |   0    |    Y     |  N   |       |   |
|  4   | phone |   varchar   | 20 |   0    |    Y     |  N   |       |   |
|  5   | area_info |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  6   | address |   varchar   | 500 |   0    |    Y     |  N   |       |   |
|  7   | remarks |   varchar   | 250 |   0    |    Y     |  N   |       |   |
|  8   | shop_id |   int   | 10 |   0    |    Y     |  N   |       | 商品仓库关联的商家Id  |
|  9   | is_chain |   tinyint   | 3 |   0    |    Y     |  N   |       |   |
|  10   | is_default |   tinyint   | 3 |   0    |    Y     |  N   |       |   |
|  11   | company |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  12   | chain_id |   int   | 10 |   0    |    Y     |  N   |       |   |
|  13   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    |   |
**表名：** <a id="ms_category">ms_category</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   bigint   | 19 |   0    |    N     |  Y   |       | ID  |
|  2   | commission_rate |   decimal   | 10 |   2    |    N     |  N   |       | 佣金比例  |
|  3   | image |   varchar   | 255 |   0    |    N     |  N   |       | 分类图标  |
|  4   | level |   int   | 10 |   0    |    N     |  N   |       | 层级  |
|  5   | name |   varchar   | 255 |   0    |    N     |  N   |       | 分类名称  |
|  6   | parent_id |   bigint   | 19 |   0    |    N     |  N   |       | 父ID  |
|  7   | sort_order |   int   | 10 |   0    |    N     |  N   |       | 排序值  |
|  8   | support_channel |   bit   | 1 |   0    |    N     |  N   |       | 是否支持频道  |
|  9   | status |   tinyint   | 3 |   0    |    N     |  N   |       | 0正常1删除  |
|  10   | create_time |   bigint   | 19 |   0    |    N     |  N   |       |   |
|  11   | update_time |   bigint   | 19 |   0    |    N     |  N   |       |   |
**表名：** <a id="ms_page_template">ms_page_template</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   bigint   | 19 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    N     |  N   |       | 模板名称  |
|  3   | client_type |   tinyint   | 3 |   0    |    N     |  N   |       | 客户端类型  |
|  4   | page_type |   tinyint   | 3 |   0    |    N     |  N   |       | 页面类型  |
|  5   | open_status |   tinyint   | 3 |   0    |    N     |  N   |       | 开启状态，同一个页面类型和客户端类型只能开启一个模板  |
|  6   | status |   tinyint   | 3 |   0    |    N     |  N   |       | 0正常1删除  |
|  7   | create_time |   bigint   | 19 |   0    |    N     |  N   |       | 创建时间  |
|  8   | update_time |   bigint   | 19 |   0    |    N     |  N   |       | 更新时间  |
**表名：** <a id="ms_template_detail">ms_template_detail</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   bigint   | 19 |   0    |    N     |  Y   |       |   |
|  2   | template_id |   bigint   | 19 |   0    |    N     |  N   |       | 页面模板id  |
|  3   | template_type |   varchar   | 255 |   0    |    N     |  N   |       | 模板类型  |
|  4   | template_data |   text   | 65535 |   0    |    N     |  N   |       | 模板数据不固定存json  |
|  5   | status |   tinyint   | 3 |   0    |    N     |  N   |       | 0正常1删除  |
|  6   | create_time |   bigint   | 19 |   0    |    N     |  N   |       | 创建时间  |
**表名：** <a id="my_access_log">my_access_log</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   varchar   | 200 |   0    |    Y     |  N   |       |   |
|  2   | count |   varchar   | 100 |   0    |    Y     |  N   |       |   |
|  3   | create_datetime |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  4   | type |   varchar   | 25 |   0    |    Y     |  N   |       |   |
|  5   | del_flag |   varchar   | 2 |   0    |    Y     |  N   |       |   |
**表名：** <a id="person">person</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
**表名：** <a id="prd_sku_storage">prd_sku_storage</a>

**说明：** SKU库存

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   bigint   | 19 |   0    |    N     |  Y   |       |   |
|  2   | sku_id |   bigint   | 19 |   0    |    N     |  N   |       |   |
|  3   | stock_quantity |   intunsigned   | 10 |   0    |    N     |  N   |   0    |   |
|  4   | trade_max_number |   int   | 10 |   0    |    N     |  N   |       |   |
|  5   | product_storage_id |   int   | 10 |   0    |    N     |  N   |       |   |
|  6   | safe_stock |   int   | 10 |   0    |    Y     |  N   |       |   |
|  7   | limit_num |   int   | 10 |   0    |    Y     |  N   |       |   |
|  8   | productCode |   varchar   | 50 |   0    |    Y     |  N   |       |   |
|  9   | shopCode |   varchar   | 50 |   0    |    Y     |  N   |       |   |
|  10   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    |   |
|  11   | pos_id |   int   | 10 |   0    |    Y     |  N   |       |   |
**表名：** <a id="tb_user">tb_user</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   bigint   | 19 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 32 |   0    |    Y     |  N   |       |   |
|  3   | phone |   varchar   | 11 |   0    |    Y     |  N   |       |   |
|  4   | operator |   varchar   | 32 |   0    |    Y     |  N   |       |   |
|  5   | gmt_create |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  6   | gmt_modified |   datetime   | 19 |   0    |    Y     |  N   |       |   |
**表名：** <a id="test">test</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | one |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | two |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  4   | createTime |   datetime   | 19 |   0    |    Y     |  N   |       |   |
**表名：** <a id="test_binlog">test_binlog</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | sex |   varchar   | 255 |   0    |    Y     |  N   |       |   |
**表名：** <a id="test_import">test_import</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | sex |   varchar   | 255 |   0    |    Y     |  N   |       |   |
**表名：** <a id="test_to_es">test_to_es</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | age |   int   | 10 |   0    |    Y     |  N   |       |   |
|  4   | username |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  5   | password |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  6   | phone |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  7   | mailbox |   varchar   | 255 |   0    |    Y     |  N   |       |   |
**表名：** <a id="ts_product_delete_log">ts_product_delete_log</a>

**说明：** 商品删除操作记录表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 主键id  |
|  2   | order_sn |   varchar   | 32 |   0    |    Y     |  N   |       | 订单编码  |
|  3   | platform_order_sn |   varchar   | 32 |   0    |    Y     |  N   |       | 平台订单编码  |
|  4   | platform_key |   varchar   | 20 |   0    |    Y     |  N   |       | p所属平台  |
|  5   | store_name |   varchar   | 50 |   0    |    Y     |  N   |       | 门店名称  |
|  6   | store_code |   varchar   | 20 |   0    |    Y     |  N   |       | 门店编码  |
|  7   | chain_id |   int   | 10 |   0    |    Y     |  N   |       |   |
|  8   | delete_time |   bigint   | 19 |   0    |    Y     |  N   |       | 删除时间  |
|  9   | operate_man |   varchar   | 50 |   0    |    Y     |  N   |       | 操作人  |
|  10   | org_id |   int   | 10 |   0    |    Y     |  N   |       | 组织机构id  |
|  11   | creation_date |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  12   | last_updated_date |   datetime   | 19 |   0    |    N     |  N   |       | 最后更新时间  |
|  13   | attribute1 |   varchar   | 30 |   0    |    Y     |  N   |       | 扩展字段1  |
|  14   | attribute2 |   varchar   | 30 |   0    |    Y     |  N   |       | 扩展字段2  |
**表名：** <a id="users">users</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id主键  |
|  2   | username |   varchar   | 20 |   0    |    N     |  N   |       | 用户名  |
|  3   | PASSWORD |   varchar   | 20 |   0    |    N     |  N   |       | 用户密码  |
|  4   | object_version_number |   int   | 10 |   0    |    Y     |  N   |       | 版本号  |
