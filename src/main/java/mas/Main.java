package mas;

import java.time.LocalDateTime;
import mas.domain.model.coach.Coach;
import mas.domain.model.coach.Specialization;
import mas.domain.model.company.*;
import mas.domain.model.company.PhoneNumber;
import mas.domain.model.conference.Conference;
import mas.domain.model.contract.Contract;
import mas.domain.model.contract.ExpirationDate;
import mas.domain.model.contract.SigningDate;
import mas.domain.model.event.*;
import mas.domain.model.event.EventDateTime;
import mas.domain.model.event.EventName;
import mas.domain.model.fight.Fight;
import mas.domain.model.fighter.Fighter;
import mas.domain.model.gala.Gala;
import mas.domain.model.person.Name;
import mas.domain.model.person.Surname;
import mas.domain.model.shared.Address;
import mas.domain.model.sponsor.Sponsor;

public class Main {

  public static void main(String[] args) {

    // unique

    System.out.println("---UNIQUE---");

    Company uc1 =
        new Sponsor(
            CompanyName.of("Unique Company Name"),
            Email.of("unique@email.com"),
            PhoneNumber.of("111222333444"),
            Address.of(10, "Main Street", "Warsaw", "Mazowieckie", "00-001"),
            NIP.of("1234"));

    Company uc2 =
        new Sponsor(
            CompanyName.of("Another Company"),
            Email.of("another@email.com"),
            PhoneNumber.of("555666777"),
            Address.of(22, "Second Avenue", "Krakow", "Malopolskie", "30-002"),
            NIP.of("1234") // nie zostanie stworzony
            );

    // Atrybutu

    System.out.println("---Atrybutu---");

    try {
      Event ea1 =
          new Conference(
              EventName.of("event 1"),
              EventDateTime.of(
                  LocalDateTime.now().minusYears(2))); // nie stworzy się event w przeszłości
      // nazwa

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    try {
      Event ea2 =
          new Conference(
              EventName.of("event 2"),
              EventDateTime.of(
                  LocalDateTime.now()
                      .plusYears(3))); // nie stworzy się event za daleko w przyszłośc
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    // Ordered
    System.out.println("---Ordered---");
    Gala gala1 =
        new Gala(EventName.of("KSW 2025"), EventDateTime.of(LocalDateTime.now().plusMonths(3)));

    Fighter fighter1 =
        new Fighter(
            Name.of("Jan"),
            Surname.of("Kowalski"),
            Address.of(5, "Mickiewicza", "Warszawa", "Mazowieckie", "00-001"));

    Fighter fighter2 =
        new Fighter(
            Name.of("Adam"),
            Surname.of("Nowak"),
            Address.of(10, "Piekna", "Kraków", "Małopolskie", "30-003"));

    Fighter fighter3 =
        new Fighter(
            Name.of("Ewa"),
            Surname.of("Wiśniewska"),
            Address.of(8, "Słoneczna", "Gdańsk", "Pomorskie", "80-001"));

    Fighter fighter4 =
        new Fighter(
            Name.of("Tomasz"),
            Surname.of("Zieliński"),
            Address.of(12, "Leśna", "Poznań", "Wielkopolskie", "60-002"));

    Fighter fighter5 =
        new Fighter(
            Name.of("Anna"),
            Surname.of("Mazur"),
            Address.of(3, "Polna", "Lublin", "Lubelskie", "20-004"));
    Fighter fighter6 =
        new Fighter(
            Name.of("Ola"),
            Surname.of("Szymańska"),
            Address.of(6, "Fajterska", "Łódź", "Łódzkie", "90-001"));
    Fighter fighter7 =
        new Fighter(
            Name.of("Piotr"),
            Surname.of("Czarny"),
            Address.of(7, "Walkowa", "Wrocław", "Dolnośląskie", "50-001"));
    Fighter fighter8 =
        new Fighter(
            Name.of("Karol"),
            Surname.of("Biały"),
            Address.of(8, "Mocna", "Szczecin", "Zachodniopomorskie", "70-001"));
    Fighter fighter9 =
        new Fighter(
            Name.of("Zuzanna"),
            Surname.of("Zielona"),
            Address.of(9, "Szybka", "Rzeszów", "Podkarpackie", "35-001"));
    Fighter fighter10 =
        new Fighter(
            Name.of("Bartek"),
            Surname.of("Czerwony"),
            Address.of(10, "Ringowa", "Katowice", "Śląskie", "40-001"));

    Fight f1 = new Fight();
    f1.addFighter(fighter1);
    f1.addFighter(fighter2);

    Fight f2 = new Fight();
    f2.addFighter(fighter3);
    f2.addFighter(fighter4);
    Fight f3 = new Fight();
    f3.addFighter(fighter5);
    f3.addFighter(fighter6);
    Fight f4 = new Fight();
    f4.addFighter(fighter7);
    f4.addFighter(fighter8);
    Fight f5 = new Fight();
    f5.addFighter(fighter9);
    f5.addFighter(fighter10);

    gala1.addFight(f1, 1);
    gala1.addFight(f2, 2);
    gala1.addFight(f3, 3);
    gala1.addFight(f4, 4);
    gala1.addFight(f5, 5);

    gala1.printFightOrder();

    // Subset
    System.out.println("---Subset---");

    Coach coach = new Coach(Name.of("Karol"), Surname.of("Nowak"), Specialization.STRIKING);

    Fighter fc =
        new Fighter(
            Name.of("Jan"),
            Surname.of("Kowalski"),
            Address.of(1, "Ringowa", "Warszawa", "Mazowieckie", "00-001"));
    Fighter fi =
        new Fighter(
            Name.of("Ewa"),
            Surname.of("Wiśniewska"),
            Address.of(2, "Sportowa", "Warszawa", "Mazowieckie", "00-002"));

    coach.addClubFighter(fc);
    coach.addClubFighter(fi);

    coach.addIndividualFighter(fi);

    System.out.println("Zawodnicy Klubowi:");
    coach.getClubFighters().stream()
        .map(f -> "\t" + f.getName() + " " + f.getSurname())
        .forEach(System.out::println);

    System.out.println("Zawodnicy Indywidualni:");
    coach.getIndividualFighters().stream()
        .map(f -> "\t" + f.getName() + " " + f.getSurname())
        .forEach(System.out::println);

    // Bag
    System.out.println("---Bag---");
    Sponsor s1 = (Sponsor) uc1;

    s1.addSponsoredFighter(fc);
    s1.addSponsoredFighter(fc);
    s1.addSponsoredFighter(fc);

    System.out.println("Fighters sponsored by " + s1.getCompanyName() + ":");
    s1.getSponsoredFighters().stream()
        .map(f -> f.getName() + " " + f.getSurname())
        .forEach(System.out::println);

    // XOR
    System.out.println("---XOR---");

    Contract c1 = Contract.createContact(fighter1, SigningDate.of(LocalDateTime.now()));

    c1.setExpiresAt(ExpirationDate.of(LocalDateTime.now().plusYears(2)));
    // c1.setIsPernament(true); //exception

    System.out.println(c1);

    // Własne
    System.out.println("---Własne---");
    for (int i = 0; i < 20; i++) {
      Gala gala =
          new Gala(EventName.of("Ksw" + i), EventDateTime.of(LocalDateTime.now().plusDays(10 + i)));
      Fight fight = new Fight();
      gala.addFight(fight, i);
      fighter2.addFight(fight);
      System.out.println("Added fight=" + fight);
    }
  }
}
