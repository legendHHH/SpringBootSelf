package com.qcl.datastructure.linkedlist;

/**
 * 单向链表
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/30
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {

        //先创建几个节点
        HeroNode heroNode1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode heroNode2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode heroNode3 = new HeroNode(3, "吴用", "智多星");
        HeroNode heroNode4 = new HeroNode(4, "林冲", "豹子头");

        //创建链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        //没有顺序加入
        /*singleLinkedList.add(heroNode1);
        singleLinkedList.add(heroNode4);
        singleLinkedList.add(heroNode2);
        singleLinkedList.add(heroNode3);*/

        //有顺序加入
        singleLinkedList.addByOrder(heroNode1);
        singleLinkedList.addByOrder(heroNode4);
        singleLinkedList.addByOrder(heroNode2);
        singleLinkedList.addByOrder(heroNode3);
        singleLinkedList.addByOrder(heroNode3);

        //显示
        singleLinkedList.showList();

        HeroNode newHeroNode = new HeroNode(2, "卢俊义666", "玉麒麟666");
        singleLinkedList.update(newHeroNode);

        System.out.println("修改后的数据～～～");
        //显示
        singleLinkedList.showList();

        System.out.println("删除节点");
        singleLinkedList.del(3);
        singleLinkedList.del(4);
        singleLinkedList.del(1);
        //singleLinkedList.del(2);
        System.out.println("删除后的链表情况");
        singleLinkedList.showList();

        //求单链表中有效节点的个数
        System.out.println("1.有效节点个数：" + getLengthOfNode(singleLinkedList.getHead()));
        System.out.println("2.有效节点个数：" + getLengthOfNode2(singleLinkedList.getHead()));
    }

    /**
     * 求单链表中节点的个数(如果是带头节点的链表，需求不统计头节点)
     *
     * @param head 链表的头节点
     * @return 返回有效节点的个数
     */
    public static int getLengthOfNode(HeroNode head) {
        //空链表
        if (head.next == null) {
            return 0;
        }

        //下一个节点
        HeroNode current = head.next;
        int count = 0;
        while (current != null) {
            count++;
            //指针后移,进行遍历
            current = current.next;
        }
        return count;
    }

    /**
     * 求单链表中节点的个数(如果是带头节点的链表，需求不统计头节点)
     *
     * @param head 链表的头节点
     * @return 返回有效节点的个数
     */
    public static int getLengthOfNode2(HeroNode head) {
        return 0;

    }
}


/**
 * 定义SingleLinkedList  来管理英雄
 */
class SingleLinkedList {
    /**
     * 先初始化头节点，头节点不要动,不存放具体的数据
     */
    private HeroNode head = new HeroNode(0, "", "");

    /**
     * 提供get方法获取内部的数据
     *
     * @return
     */
    public HeroNode getHead() {
        return head;
    }

    /**
     * 添加节点到单向链表
     * <p>
     * 思路：当不考虑编号时
     * 1.找到当前链表的最后节点
     * 2.将最后这个节点的next指向新的节点
     */
    public void add(HeroNode heroNode) {

        //因为head节点不能动，因此需要一个辅助遍历temp
        HeroNode temp = head;

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
        //将最后的这个节点的next指向新的节点
        temp.next = heroNode;
    }

    /**
     * 第二种方式在添加英雄时，根据排名将英雄插入到指定位置（如果有这个排名，则添加失败，并给出提示）
     */
    public void addByOrder(HeroNode heroNode) {

        //因为head节点不能动，因此需要一个辅助遍历temp来帮助找到添加的位置
        //因为单链表，因为我们找到的temp是位于添加位置的前一个节点，否则加不进去
        HeroNode temp = head;

        //添加的编号是否存在，默认为false
        boolean flag = false;

        while (true) {
            //说明temp已经在链表的最后
            if (temp.next == null) {
                break;
            }

            //位置找到，就在temp的后面插入
            if (temp.next.no > heroNode.no) {
                break;
            } else if (temp.next.no == heroNode.no) {
                //说明正在添加的编号数据已经存在
                flag = true;
                break;
            }
            //后移遍历当前链表
            temp = temp.next;
        }

        //判断flag的值
        if (flag) {
            //不能添加
            System.out.printf("准备插入的英雄的编号%d已经存在,不能加入\n", heroNode.no);
        } else {
            //插入到链表中，temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    /**
     * 修改节点信息，根据no编号来修改即no编号不能改
     * 根据newHeroNode的no编号来修改数据
     */
    public void update(HeroNode newHeroNode) {
        //判断是否为空
        if (head.next == null) {
            System.out.println("链表为空～～～");
            return;
        }
        //找到需要修改的节点，根据no编号
        HeroNode temp = head.next;
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
     * 删除节点
     * head不能动，先找到要删的这个节点的前一个节点temp
     * temp.next = temp.next.next
     * 被删除的节点，将不会有其他的引用指向，会被垃圾回收机制回收
     *
     * @param no
     * @return
     */
    public void del(int no) {
         HeroNode temp = head;

         if (temp.next == null) {
             System.out.println("当前链表为空");
         }
         while (true) {
             if (temp.next == null) {
                 System.out.println("最后一个元素");
                 break;

             }
             if (temp.next.no == no) {
                 temp.next = temp.next.next;
                 break;
             }
             temp = temp.next;
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
        HeroNode temp = head.next;
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
 * 定义一个HeroNode，每个HeroNode对象就是一个节点
 */
class HeroNode {
    public int no;
    public String name;
    public String nickName;

    /**
     * 指向下一个节点
     */
    public HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public HeroNode getNext() {
        return next;
    }

    public void setNext(HeroNode next) {
        this.next = next;
    }
}