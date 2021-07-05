package com.bank.credit.card;

import com.bank.credit.card.constants.BrandTest;
import com.bank.credit.card.constraints.validation.CreditCardNumberTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BrandTest.class, CreditCardNumberTest.class})
public class AllTests {
}
