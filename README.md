System zarządzania wind (Krzysztof Uszko)

**Struktura:**\
System jest napisany w JAVIE, sam algorytm do zarządzania Windami
składa się z 2 klas - Elevator i ElevatorSystem. Elevator to klasa 
odpowiadająca za sterowanie pojedyńczą windą (zaimplementowane są w niej
funkcje step,pickup,status oraz inne - pomocniczne). ElevatorSystem to
klasa odpowiadająca za cały zbiór wind - pozwala na m.in na wykonanie 
kroku symulacji dla całego systemu, oraz zarządzanie poszczególnymi windami 
w systemie.

**Algorytm:**\
Algorytm opiera się na dwóch tablicach - jedna to "właściwa" kolejka
która obsługuje kolejne zatrzymania się windy, druga przechowuje
informacje o kolejnych "zamówieniach" windy. Dzięki takiej architekturze 
można optymalnie obsłużyć kolejne zapytania. \
Algorytm skupia się na obsłużeniu zamówień według kolejności ich zgłaszania,
jednak kiedy istnieje możliwość optymalizacji trasy jest ona brana pod uwagę.
Zaimplementowałem również system pozwalający obsłużyć więcej niż jedno przyciśnięte
piętro - tutaj również pierszeństwo mają piętra odpowiadające wciśniętemu guzikowi 
przy zamawianiu (guzik w dół na 5 piętrze i kliknięto: 7,2,3 -> winda odwiedzi kolejno: 3,2,7 piętro)

**Przykłady:**\
W celu lepszej wizualizacji algorytmu podam parę przykładów:\
1. Zamówienia:[7,-1],[4,1],[6,-1]: winda jedzie najpierw 7 piętro, kierunek: dół -
zostaje wciśnięte piętro 0. Winda jedzie w dół, algorytm zauważa że na 6 piętrze również jest 
zamówienie w dół, które można przy okazji obsłużyć: otwiera się na 6 piętrze - wciśnięto piętro: 2,3. \
Winda otwiera się kolejno na piętrach 3,2,0, po czym wraca do zamówienia [4,1].
Na 4 piętrze kliknięto 9,2,7: Winda odwiedza kolejno piętra: 7,9,2 (priorytet na kierunek zamówienia)

2. Zamówienia:[8,-1],[9,-1]: Winda pojedzie najpierw na 8 piętro. Na 8 piętrze kliknięto
   2,3. Winda obsłuży zamówienia i odwiedzi kolejno piętro 3 i 2, po czym obsłuży zamówienia z 
   piętra 9. Wydaje się to nieoptymalne, jednak należy uwzględnić pierszeństwo przy zamówieniu -
   np, gdyby zamówienia były dostarczane z kolejnych wyższych piętr (np. 9,10,11) to osoba która 
   zamówiła windę na 8 piętrze mogła by się zdenerwować - jeżdząc do góry, pomimo że zamówiła pierwsza.
3. Zamówienie:[5,-1]: Winda jedzie na 5 piętro, wsiadają osoby które klikają: 8,7,9 -> Winda pojedzie odpowiednio
na 7,8,9 piętro - zaimplementowałem funkcję, która pomimo wciśnięcia złego kierunku wciąż sortuje naciśnięte piętra. 

**Symulacja:**\
Do algorytmu dołączone jest również GUI wraz z symulacją. Jest dosyć prosta - napisana
przy pomocy bibliotek Swing/AWT. Domyślnie po uruchomieniu programu w części głównej pojawa się
"wizualizacja" 16 wind (system obsługuje dowolną ilość). Sama wizualizacja jest statycznie zaprogramowana
na 10 pięter (jednak system jest w stanie obsłużyć dowolną ilość :) ).Winda domyślnie ma kolor zielony,
kiedy się otwiera przyjmuje kolor niebieski, czerwone kółko to oczekujące zamówienie. Z lewej 
strony widzimy obecny numer piętra oraz pierwsze piętro z kolejki.
Poniżej wizualizacji znajdziemy 4 przyciski interfejsu:\
1. Wykonaj krok symulacji - rusza wszystkimi windami o jedno piętro (jeżeli nie ma zakolejkowanych zleceń winda stoi w miejscu),
 również kiedy do windy wsiadają osoby pozostaje ona przez jeden krok na tym samym piętrze.
2. Zamów winde - otwiera okno zamówienia z 4 polami: piętro zamówienia (na którym piętrze klikany jest przycisk),
    id windy, kierunek (1 do góry,-1 w dół), piętra: cyfra, lub cyfry oddzielone ",".
3. Dodaj winde - dodaje winde o danym ID
4. Usun winde - usuwa winde o danym ID

**Jak odpalić**\
Projekt wrzucam jako projekt IntelliJ. W przypadku używania tego samego IDE, należy tylko zaimportować
i odpalić, w przypadku innego IDE pliki projektu znajdują się w folderze: src -> com -> company. Metoda main znajduje się 
w pliku Main.java.
