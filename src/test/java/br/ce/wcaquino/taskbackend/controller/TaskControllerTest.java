package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

	@Mock
	private TaskRepo taskRepo;

	@InjectMocks
	TaskController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void retornaTodasAsTasks() {
		Task todo1 = new Task();
		todo1.setTask("Descricao1");
		todo1.setDueDate(LocalDate.now());
		Task todo2 = new Task();
		todo1.setTask("Descricao2");
		todo1.setDueDate(LocalDate.now());
		Task todo3 = new Task();
		todo1.setTask("Descricao3");
		todo1.setDueDate(LocalDate.now());
		List<Task> retorno = Arrays.asList(todo1,todo2,todo3);
		Mockito.when(taskRepo.findAll()).thenReturn(retorno);
		Assert.assertEquals(retorno, controller.findAll());
	}

	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		Task todo = new Task();
		todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}

	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task todo = new Task();
		todo.setTask("Descricao");
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}

	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.now().minusYears(5));
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.now());
		controller.save(todo);
		Mockito.verify(taskRepo).save(todo);
	}

}
