import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ModelTest {
	
	private Model model;
	
	private Term entry1 = new Term(0, 15, true); // 15
	private Term entry2 = new Term(0, 10, true); // 10
	private Term entry3 = new Term(0, 0, true);  // 0
	private Term entry4	= new Term(0, 5, true);  // 5
	private Term entryPI = new Term(0, 3.14159, true);

	@Before
	public void setup() throws Exception {
		model = new Model();
	}
	
	/*
	 * Binary and Unary Operations
	 */
	
	//Addition
	
	@Test
	public void testAdd() {
		model.insert(entry1);
		model.insert(entry2);
		model.add();
		double expected = 25;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	@Test
	public void AddOneElement() {
		model.insert(entry1);
		model.add();
		double expected = 15;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	//Subtraction
	
	@Test
	public void testSubtract() {
		model.insert(entry1);
		model.insert(entry2);
		model.subtract();
		double expected = 5;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	@Test
	public void SubtractOneElement() {
		model.insert(entry1);
		model.subtract();
		double expected = 15;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	//Multiplication
	
	@Test
	public void testMultiply() {
		model.insert(entry1);
		model.insert(entry2);
		model.multiply();
		double expected = 150;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	@Test
	public void MultiplyOneElement() {
		model.insert(entry1);
		model.multiply();
		double expected = 15;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	//Division
	
	@Test
	public void DivideOneElement() {
		model.insert(entry1);
		model.divide();
		double expected = 15;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	@Test
	public void testDivide() {
		model.insert(entry1);
		model.insert(entry2);
		model.divide();
		double expected = 1.5;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	@Test
	public void testDivideByZero() {
		model.insert(entry1); //15
		model.insert(entry3); //0
		assertTrue(model.divide());
	}
	
	//Factorial
	
	@Test
	public void testFactorial() {
		long n = 5;
		long expected = 120;
		assertEquals(FactorialExpression.factorial(n), expected);
	}
	
	@Test
	public void testNegativeFactorial() {
		FactorialExpression.factorial(-1);
		assertTrue(FactorialExpression.isNegative());
	}
	
	@Test
	public void testZeroFactorial() {
		long n = 0;
		long expected = 1;
		assertEquals(FactorialExpression.factorial(n), expected);
	}
	
	//Change sign
	
	@Test
	public void testChangeSign() {
		model.insert(entry1);
		model.negate();
		double expected = -15;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	// Trigonometric Functions
	
	@Test
	public void testSin() {
		model.insert(entryPI);
		model.sin();
		double expected = 0;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	@Test
	public void testCos() {
		model.insert(entryPI);
		model.cos();
		double expected = 1;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	
	@Test
	public void OrderOfOperations() {
		model.insert(entry1);
		model.insert(entry2);
		model.insert(entry4);
		model.subtract();
		model.subtract();
		double expected = 10;
		assertEquals(model.returnStackTop(), expected, 0.0001);
	}
	
	/*
	 * Calculator Functions
	 */
	
	@Test
	public void UndoRemoveFromStack() {
		model.insert(entry1);
		model.insert(entry2);
		model.undo();
		assertEquals(model.size(), 1);
		assertEquals(model.returnStackTop(), entry1.evaluate(1.0), 0.0001);
	}
	
	@Test
	public void UndoLastExpression() {
		model.insert(entry1);
		model.insert(entry2);
		model.subtract();
		model.undo();
		assertEquals(model.size(), 2);
		assertEquals(model.returnStackTop(), entry2.evaluate(1.0), 0.0001);
	}
	
	@Test
	public void UndoNothing() {
		String expected = "nothing to undo !";
		assertEquals(model.undo(), expected);
	}
	
	@Test
	public void testPurge() {
		model.insert(entry1);
		model.insert(entry2);
		model.add();
		model.insert(entry2);
		model.subtract();
		model.purge();
		assertEquals(model.size(), 0);
	}
	
	@Test
	public void testNoBrackets() {
		model.insert(entry1);
		model.insert(entry2);
		model.insert(entry4);
		model.add();
		model.add();
		String expected = "15+10+5";
		assertEquals(model.lastExpression().toString(), expected);
	}
	
	@Test
	public void testCheckBrackets() {
		model.insert(entry1);
		model.insert(entry2);
		model.insert(entry4);
		model.subtract();
		model.subtract();
		String expected = "15-(10-5)";
		assertEquals(model.lastExpression().toString(), expected);
	}
	
	@Test
	public void testCheckBrackets2() {
		model.insert(entry1);
		model.insert(entry2);
		model.add();
		model.insert(entry2);
		model.insert(entry4);
		model.subtract();
		model.multiply();
		String expected = "(15+10)*(10-5)";
		assertEquals(model.lastExpression().toString(), expected);
	}
	
	
}