## LPG日志收集系统



https://grafana.com/docs/loki/latest/installation/local/

### 简介
LPG日志收集方案内存占用很少，经济且高效！它不像ELK日志系统那样为日志建立索引，而是为每个日志流设置一组标签。

- Loki：聚合并存储日志数据，可以作为Grafana的数据源，为Grafana提供可视化数据。

- Promtail：日志收集器，有点像Filebeat，可以收集日志文件中的日志，并把收集到的数据推送到Loki中去。

- Grafana：从Loki中获取日志信息，进行可视化展示。

![](https://img2020.cnblogs.com/blog/1231979/202107/1231979-20210721085716139-1974149543.png)


[LP下载地址](https://github.com/grafana/loki/releases/)
[Grafana-windows下载地址](https://grafana.com/grafana/download?platform=windows)
[Grafana-linux下载地址](https://grafana.com/grafana/download?platform=linux)




[loki-local-config.yaml配置文件信息](https://raw.githubusercontent.com/grafana/loki/master/cmd/loki/loki-local-config.yaml)
[loki-local-config.yaml配置文件信息](https://raw.githubusercontent.com/grafana/loki/master/cmd/loki/loki-local-config.yaml)