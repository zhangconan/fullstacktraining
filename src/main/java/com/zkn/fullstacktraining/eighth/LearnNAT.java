package com.zkn.fullstacktraining.eighth;

/**
 * Created by wb-zhangkenan on 2017/2/14.
 * 电脑跟外网的网站通讯的时候，是把数据报文直接发给谁的？
 */
public class LearnNAT {

    /**
     * 使用私有IP的主机如何和因特网或者其他外网上的主机进行通信呢？
     *  使用NAT(Network Address Translation)网络地址转换。支持NAT的设备有路由器、防火墙、和独立的NAT设备。NAT负责在私有网络地址和一个或多个
     *  全局分配的IP地址直接进行翻译。工作原理：
     *      静态：将私有网络上的部分或所有主机的私有IP地址都映射为一个固定的、全局分配的地址。
     *      地址池：NAT设备有一组全局分配的IP地址可用，它会将其中之一动态地分配给需要与外部网络中的对等实体进行通信的主机。
     *      PAT：也称为PAT(Port Address Translation,端口地址转换),只有一个全局分配地址时可以使用这种方法。使用PAT时，
     *          所有私有地址都映射为同样的外部地址，但输出分组的源端口会被修改成唯一的值，通过它可以将输入分组与私有网络地址相关联。
     *
     */
}













