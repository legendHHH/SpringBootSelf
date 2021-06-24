package com.qcl.datastructure.linkedlist;

/**
 * 双向链表
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/24
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {
        System.out.println("双向链表测试.......");

        //先创建几个节点
        HeroNode2 heroNode1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 heroNode2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 heroNode3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 heroNode4 = new HeroNode2(4, "林冲", "豹子头");

        //创建双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(heroNode1);
        doubleLinkedList.add(heroNode2);
        doubleLinkedList.add(heroNode3);
        doubleLinkedList.add(heroNode4);

        doubleLinkedList.showList();

        //修改链表数据
        HeroNode2 newHeroNode = new HeroNode2(4, "公孙胜", "入云龙");
        doubleLinkedList.update(newHeroNode);
        System.out.println("修改后的双向链表情况");
        doubleLinkedList.showList();

        //删除双向链表数据
        doubleLinkedList.delete(3);
        System.out.println("删除后的双向链表情况");
        doubleLinkedList.showList();

        //TODO 双向链表的第二中添加方式,按照编号顺序--->按照单链表的顺序添加稍作修改即可
    }
}

/**
 * 定义DoubleLinkedList  来管理英雄
 */
class DoubleLinkedList {
    /**
     * 先初始化头节点，头节点不要动,不存放具体的数据
     */
    private HeroNode2 head = new HeroNode2(0, "", "");

    /**
     * 提供get方法获取内部的数据
     *
     * @return
     */
    public HeroNode2 getHead2() {
        return head;
    }


    /**
     * 添加节点到双向链表的最后
     * <p>
     * 思路：
     * 1.先找到双向链表的最后这个节点
     * 2.temp.next=new HeroNode();
     * 3.new HeroNode().pre = temp;
     */
    public void add(HeroNode2 heroNode) {

        //因为head节点不能动，因此需要一个辅助遍历temp
        HeroNode2 temp = head;

        //遍历链表，找到最后
        while (true) {
            //找到链表的最后
            if (temp.next == null) {
                break;
            }
            //如果没有找到最后，将temp后移
            temp = temp.next;
        }
        //当while退出循环时，temp就指向了链表的最后
        //形成一个双向链表
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    /**
     * 修改节点信息，根据no编号来修改即no编号不能改
     * 根据newHeroNode的no编号来修改数据
     * <p>
     * 可以看到双向链表的节点内容修改跟单链表一样只是节点类型修改成了HeroNode2
     */
    public void update(HeroNode2 newHeroNode) {
        //判断是否为空
        if (head.next == null) {
            System.out.println("链表为空～～～");
            return;
        }
        //找到需要修改的节点，根据no编号
        HeroNode2 temp = head.next;
        //表示是否找到该节点
        boolean flag = false;
        //定义一个辅助变量
        while (true) {
            //已经遍历完链表
            if (temp == null) {
                break;
            }
            //找到编号一致的
            if (temp.no == newHeroNode.no) {
                flag = true;
                break;
            }

            //不停的往后移动指针不然就会死循环
            temp = temp.next;
        }
        //根据flag 判断是否找到要修改的节点
        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickName = newHeroNode.nickName;
        } else {
            System.out.printf("没有找到编号%d 的数据，不能修改", newHeroNode.no);
        }

    }

    /**
     * 双向链表删除一个节点
     * <p>
     * 1.对于双向链表可以直接找到被删除的节点
     * 2.找到后,自我删除即可
     *
     * @param no
     * @return
     */
    public void delete(int no) {

        if (head.next == null) {
            System.out.println("当前链表为空");
            return;
        }

        //辅助变量(指针)
        HeroNode2 temp = head.next;

        //标志是否找到待删除节点
        boolean flag = false;
        while (true) {
            //已经到链表最后节点的next
            if (temp == null) {
                System.out.println("最后一个元素");
                break;

            }
            if (temp.no == no) {
                //找到代删除的节点的前一个节点temp
                flag = true;
                break;
            }
            temp = temp.next;
        }

        //判断flag
        if (flag) {
            //可以删除
            temp.pre.next = temp.next;

            //如果是最后一个节点,就不需要执行下面这句话,否则出现空指针异常
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.printf("要删除的%d 节点不存在!!!!", no);
        }
    }

    /**
     * 显示链表(遍历)
     */
    public void showList() {
        //先判断链表数据为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        //因为头节点不能动因此我们需要一个辅助变量来遍历
        HeroNode2 temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            //输出节点信息
            System.out.println(temp);
            //将temp节点后移,不然就是死循环
            temp = temp.next;
        }
    }
}

/**
 * 定义一个HeroNode2,每个HeroNode2对象就是一个节点
 */
class HeroNode2 {
    public int no;
    public String name;
    public String nickName;

    /**
     * 指向下一个节点,默认为null
     */
    public HeroNode2 next;

    /**
     * 指向前一个节点,默认为null
     */
    public HeroNode2 pre;

    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

}
