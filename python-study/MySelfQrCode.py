from segno import helpers

# 生成个人名片，可以直接保存到联系人中
data = helpers.make_mecard(
    name='legend',
    email='chunlin.qi@hand-china.com',
    phone='15607835338'
)

# 生成个人名片保存二维码
data.save('myselfQrCode.png', scale=10)
