package di;

import accounts.factories.BankAccountFactory;
import accounts.serialization.BankAccountGsonSerializer;
import accounts.serialization.BankAccountJsonSerializer;
import accounts.serialization.BankAccountXmlSerializer;
import accounts.services.AccountNumberGenerator;
import accounts.services.BankAccountService;
import accounts.services.IBankAccountService;
import accounts.services.decorators.BankAccountWithLoggerService;
import currency.Currency;
import currency.CurrencyConverterService;
import currency.MoneyFormatter;
import history.TransferLoggerService;
import person.factories.AccountOwnerFactory;
import person.services.AccountOwnerService;
import transfer.AccountTransferService;
import transfer.DepositTransferService;
import transfer.WithdrawTransferService;

public class ContainerFactory {

    /** Měna, na kterou se v konzoli přepočítávají všechny částky. */
    private static final Currency DISPLAY_CURRENCY = Currency.EUR;

    public Container createContainer() {
        return build(false);
    }

    public Container createContainerWithLogger() {
        return build(true);
    }

    private Container build(boolean withLogger) {
        Container container = new Container();

        AccountOwnerService ownerService = new AccountOwnerService(new AccountOwnerFactory());
        container.register(AccountOwnerService.class, ownerService);

        BankAccountFactory bankAccountFactory = new BankAccountFactory(new AccountNumberGenerator("0800"));
        IBankAccountService accountService = new BankAccountService(bankAccountFactory);
        if (withLogger) {
            accountService = new BankAccountWithLoggerService(accountService);
        }
        container.register(IBankAccountService.class, accountService);

        CurrencyConverterService converter = new CurrencyConverterService();
        container.register(CurrencyConverterService.class, converter);
        container.register(MoneyFormatter.class, new MoneyFormatter(converter, DISPLAY_CURRENCY));

        TransferLoggerService historyService = new TransferLoggerService();
        container.register(TransferLoggerService.class, historyService);

        container.register(DepositTransferService.class, new DepositTransferService(historyService));
        container.register(WithdrawTransferService.class, new WithdrawTransferService(historyService));
        container.register(AccountTransferService.class, new AccountTransferService(historyService, converter));

        container.register(BankAccountJsonSerializer.class, new BankAccountJsonSerializer());
        container.register(BankAccountXmlSerializer.class, new BankAccountXmlSerializer());
        container.register(BankAccountGsonSerializer.class, new BankAccountGsonSerializer());

        return container;
    }
}