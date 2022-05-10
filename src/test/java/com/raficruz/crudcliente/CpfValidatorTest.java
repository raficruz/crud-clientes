package com.raficruz.crudcliente;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.raficruz.crudcliente.model.validator.CpfValidator;

class CpfValidatorTest {

	@Test
	void whenCpfIsValid() {
		assertTrue(new CpfValidator().isValid("45733524065", null));
		assertTrue(new CpfValidator().isValid("02087925038", null));
		assertTrue(new CpfValidator().isValid("90158880005", null));
		assertTrue(new CpfValidator().isValid("92480179087", null));
		assertTrue(new CpfValidator().isValid("01713012006", null));
		assertTrue(new CpfValidator().isValid("55173647051", null));
		assertTrue(new CpfValidator().isValid("30408228024", null));
		assertTrue(new CpfValidator().isValid("24212610078", null));
		assertTrue(new CpfValidator().isValid("29902087043", null));
		assertTrue(new CpfValidator().isValid("88063920007", null));
		assertTrue(new CpfValidator().isValid("34255839050", null));
	}

	@Test
	void whenCpfIsInvalid() {
		assertFalse(new CpfValidator().isValid("00000000000", null));
		assertFalse(new CpfValidator().isValid("11111111111", null));
		assertFalse(new CpfValidator().isValid("22222222222", null));
		assertFalse(new CpfValidator().isValid("33333333333", null));
		assertFalse(new CpfValidator().isValid("44444444444", null));
		assertFalse(new CpfValidator().isValid("55555555555", null));
		assertFalse(new CpfValidator().isValid("66666666666", null));
		assertFalse(new CpfValidator().isValid("77777777777", null));
		assertFalse(new CpfValidator().isValid("88888888888", null));
		assertFalse(new CpfValidator().isValid("99999999999", null));
		assertFalse(new CpfValidator().isValid("15712583878", null));
		assertFalse(new CpfValidator().isValid("123456789012", null));
		assertFalse(new CpfValidator().isValid("", null));
		assertFalse(new CpfValidator().isValid(null, null));
	}
}
