package com.practica.integracion;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.naming.OperationNotSupportedException;

import org.junit.jupiter.api.BeforeEach;
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
public class TestValidUser {
	
	@Mock
	private AuthDAO mockAuthDao;
	
	@Mock
	private GenericDAO mockGenericDao;
	
	@Test 
	public void testConstructor () {
		SystemManager manager = new SystemManager(mockAuthDao,mockGenericDao);
		
		assertNotNull(manager);
	}

	//Encuentra el validId que se pasa como parámetro al método startRemoteSystem, devuelve la lista
	@Test
	public void testStartRemoteSystemWithValidUserAndSystem1() throws Exception{

		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String validId = "12345";
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno","dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(),"[uno, dos]");
		
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser,"where id=" + validId);
	}
	
	//No encuentra el validId que se pasa como parámetro al método startRemoteSystem, por lo que devolverá null
	@Test
	public void testStartRemoteSystemWithValidUserAndSystem2() throws Exception{
		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String validId = "56789";
		ArrayList<Object> lista = new ArrayList<>();
		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(),"[]");
		
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser,"where id=" + validId);
	}
	
	//Encuentra el validId que se pasa como parámetro al método startRemoteSystem, devuelve la lista
	@Test
	public void testStopRemoteSystemWithValidUserAndSystem1() throws Exception{

		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String validId = "12345";
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno","dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(),"[uno, dos]");
		
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser,"where id=" + validId);
	}
	
	//No encuentra el validId que se pasa como parámetro al método startRemoteSystem, por lo que devolverá null
	@Test
	public void testStopRemoteSystemWithValidUserAndSystem2() throws Exception{
		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String validId = "56789";
		ArrayList<Object> lista = new ArrayList<>();
		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(),"[]");
		
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser,"where id=" + validId);
	}
	
	//Testeamos aniadir un usuario completamente nuevo al sistema
	@Test
	public void testAddRemoteSystemWithValidUserAndSystem1() throws Exception {
		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		User newUser = new User("99", "Pedro", "Gimenez", "Guadalajara", new ArrayList<Object> (Arrays.asList(3, 4)));
		when(mockGenericDao.updateSomeData(validUser, newUser)).thenReturn(true);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		assertAll(()->manager.addRemoteSystem(validUser.getId(),newUser));
		
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).updateSomeData(validUser, newUser);
	}
	
	//Testeamos actualizar un usuario ya existente
	@Test
	public void testAddRemoteSystemWithValidUserAndSystem2() throws Exception {
		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		User updateUser = new User("1", "NoEsAna", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList(1, 2)));
		when(mockGenericDao.updateSomeData(validUser, updateUser)).thenReturn(true);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		assertAll(()->manager.addRemoteSystem(validUser.getId(),updateUser));
		
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).updateSomeData(validUser, updateUser);
	}
	
	//Testeamos eliminar un objeto existente del sistema
	@Test
	public void testDeleteRemoteSystemWithValidUserAndSystem1() throws Exception {
		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String existsId = "12345";
		when(mockGenericDao.deleteSomeData(validUser,existsId)).thenReturn(true);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		assertAll(()->manager.deleteRemoteSystem(validUser.getId(),existsId));
		
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).deleteSomeData(validUser, existsId);
	}
	
	//Testeamos eliminar un objeto que no existe en el sistema
	@Test
	public void testDeleteRemoteSystemWithValidUserAndSystem2() throws Exception {
		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object> (Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String noExistsId = "77777";
		when(mockGenericDao.deleteSomeData(validUser,noExistsId)).thenReturn(false);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		assertThrows(SystemManagerException.class,()->manager.deleteRemoteSystem(validUser.getId(),noExistsId),"cannot delete remote: does remote exists?");
		
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).deleteSomeData(validUser, noExistsId);
	}
}
