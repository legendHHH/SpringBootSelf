from segno import helpers

# Python二维码技术项目2

# 生成个人名片，可以直接保存到联系人中
data = helpers.make_mecard(
    name='legend',
    email='xxxxxx',
    phone='xxxxxx'
)

# 生成个人名片保存二维码
data.save('myselfQrCode.png', scale=10)
