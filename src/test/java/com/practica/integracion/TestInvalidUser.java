package com.practica.integracion;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.naming.OperationNotSupportedException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
	@Mock
	private AuthDAO mockAuthDao;
	
	@Mock
	private GenericDAO mockGenericDao;
	
	@Test
	public void testStartRemoteSystemWithInvalidUser1() throws Exception{

		User invalidUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList()));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(invalidUser);
		
		String validId = "12345";
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno","dos"));
		when(mockGenericDao.getSomeData(invalidUser, "where id=" + validId)).thenThrow(OperationNotSupportedException.class);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		assertThrows(SystemManagerException.class,() -> manager.startRemoteSystem(invalidUser.getId(), validId));
		
		ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao).getSomeData(invalidUser,"where id=" + validId);
	}
	
	@Test
	public void testStopRemoteSystemWithInvalidUser1() throws Exception{

		User invalidUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList()));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(invalidUser);
		
		String validId = "12345";
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno","dos"));
		when(mockGenericDao.getSomeData(invalidUser, "where id=" + validId)).thenThrow(OperationNotSupportedException.class);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		assertThrows(SystemManagerException.class,() -> manager.stopRemoteSystem(invalidUser.getId(), validId));
		
		ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao).getSomeData(invalidUser,"where id=" + validId);
	}
	
	@Test
	public void testAddRemoteSystemWithInvalidUser1() throws Exception {
		User invalidUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList()));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(invalidUser);
		
		User newUser = new User("99", "Pedro", "Gimenez", "Guadalajara", new ArrayList<Object> (Arrays.asList(3, 4)));
		when(mockGenericDao.updateSomeData(invalidUser, newUser)).thenThrow(OperationNotSupportedException.class);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		assertThrows(SystemManagerException.class,()->manager.addRemoteSystem(invalidUser.getId(),newUser));
		
		ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao).updateSomeData(invalidUser, newUser);
	}
	
	@Test
	public void testDeleteRemoteSystemWithInvalidUser1() throws Exception {
		User invalidUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList()));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(invalidUser);
		
		String existsId = "12345";
		when(mockGenericDao.deleteSomeData(invalidUser,existsId)).thenThrow(OperationNotSupportedException.class);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		assertThrows(SystemManagerException.class,()->manager.deleteRemoteSystem(invalidUser.getId(),existsId));
		
		ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao).deleteSomeData(invalidUser, existsId);
	}
}
