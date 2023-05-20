package br.com.openbank.model.embedded;

import br.com.openbank.model.enums.TypeCard;
import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter @Setter
public abstract class Card {
    private String number;
    private String flag;
    private String password;
    private boolean active;
    private TypeCard typeCard;

    public Card(TypeCard typeCard){
        super();
        String flag = generateFlag();
        this.number = generateNumber(flag);
        this.flag = flag;
        this.password = generatePassword();
        this.active = true;
        this.typeCard = typeCard;
    }

    private String generateFlag(){
        String[] flags = { "mastercard", "visa" };
        Faker faker = new Faker();
        return flags[faker.random().nextInt(flags.length)];
    }

    private String generateNumber(String flag){
        Faker faker = new Faker();
        if(flag.equals("mastercard")){
            return faker.finance().creditCard(CreditCardType.MASTERCARD);
        }
        return faker.finance().creditCard(CreditCardType.VISA);
    }

    private String generatePassword(){
        Random random = new Random();
        int number = random.nextInt(10000);
        return String.format("%04d", number);
    }
}
