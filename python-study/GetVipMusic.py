# 若需要安装 则直接运行  pip install urllib  (包名)
import json
from urllib import parse

# 该网站有反爬机制，要模拟浏览器来进行伪装
import requests

headers = {
    # 这里是使用对象kv键值对的形式不然会报错
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36',
    'Referer': 'http://www.kuwo.cn/search/list?key=%E5%91%A8%E6%9D%B0%E4%BC%A6',
    'csrf': 'U6AYYWZ0QSI',
    'Cookie': '_ga=GA1.2.586524030.1615962324; Hm_lvt_cdb524f42f0ce19b169a8071123a4797=1623600328; Hm_lpvt_cdb524f42f0ce19b169a8071123a4797=1623600328; _gid=GA1.2.1435769468.1623600328; _gat=1; kw_token=U6AYYWZ0QSI'
}


# 定义函数功能最终要获取歌曲相应的id值和歌曲的名字
def get_music_url():
    # 把数据放到列表中
    music_list = []
    # 歌手名字
    singer = str(input("请输入下载歌手的名字："))
    number = int(input("请输入下载的页数："))

    # 循环遍历
    for i in range(1, number + 1):
        parameters = {
            'key': singer,
            # 页数
            'pn': i,
            # 歌曲的数量
            'rn': 10,
            'httpsStatus': 1,
            'reqId': '766a8fc58e0da8134fb2fff0e179e59f'
        }
        # {"code":200,"curTime":1623600348325,"data":["雾里","半生雪","大风吹","别错过","今夜无人入睡","野摩托","银河与星斗","踏山河","爱难求情难断","白月光与朱砂痣"],"msg":"success","profileId":"site","reqId":"766a8fc58e0da8134fb2fff0e179e59f","tId":""}
        # http://www.kuwo.cn/api/www/search/searchMusicBykeyWord?key=%E5%91%A8%E6%9D%B0%E4%BC%A6&pn=1&rn=30&httpsStatus=1&reqId=37244530-cc61-11eb-8bdd-87cd01d875aa
        data = 'http://www.kuwo.cn/api/www/search/searchMusicBykeyWord?'
        url_data = parse.urlencode(parameters)
        url = data + url_data
        music_list.append(url)

        return music_list


# 获取对应每一首歌的的rid的值个歌曲的名字
def get_music_data(url):
    # 定义空列表
    list_data = []
    print(url)
    response = requests.get(url, headers=headers)
    response.encoding = "utf-8"

    # 获取响应的文本信息
    html = response.text
    # print(html)
    # 转化json
    result = json.loads(html)
    data = result['data']['list']
    for i in data:
        rid = i['rid']
        name = i['name']
        list_data.append((rid, name))
    return list_data


# 获取歌曲的mp3文件
def get_music_mp3(rid):
    # 定义空列表
    list_data = []
    # url = 'http://www.kuwo.cn/url?format=mp3&rid={}&response=url&type=convert_url3&br=128kmp3&from=web&t=1623602926957&httpsStatus=1&reqId=38ae58e1-cc67-11eb-903d-1d12ef1e7fac'.format(rid)
    url = 'http://www.kuwo.cn/url?format=mp3&rid={}&response=url&type=convert_url3&br=128kmp3&from=web&t=1623634939497&httpsStatus=1&reqId=c1a44ca1-ccb1-11eb-a798-e1afd5aa0fcf'.format(
        rid)
    response = requests.get(url, headers=headers)
    response.encoding = "utf-8"

    # 获取响应的文本信息
    html = response.text
    print("获取到的详情文本信息：" + html)
    # 转化json
    result = json.loads(html)
    music_url = result['url']
    list_data.append(music_url)

    return list_data


# 最后一步下载歌曲，定义函数来存放执行代码块
def main():
    for url in get_music_url():
        for i in get_music_data(url):
            # 歌曲的rid
            rid = i[0]
            name = i[1]
            for music_url in get_music_mp3(rid):
                # 异常处理
                try:
                    # 这里需要指定下载路径不然会报File文件夹找不到异常
                    with open('../music/{}.mp3'.format(name), 'wb') as file:
                        print('正在下载{}'.format(name), end='')
                        music = requests.get(music_url)

                        file.write(music.content)
                        file.close()
                        print('\t下载完成')
                except Exception as e:
                    print('下载出现异常,异常信息e.message:\t', e.message)


if __name__ == '__main__':
    main()
