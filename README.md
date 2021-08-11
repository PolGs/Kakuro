# Kakuro
## Joc programat en java per a jugar Kakuros



![alt text](https://user-images.githubusercontent.com/19478700/128951594-66513ed4-5539-432e-96db-dc49633310e5.png))

### You will find the user manual and screenshots in <a href="https://github.com/PolGs/Kakuro/raw/a01855866c12885b9c0e6721a476c58b0e049389/Entrega%203.0.pdf">Entrega3.0.pdf</a>

Aquest programa serveix per jugar i resoldre kakuros. Permet fer partides per
diferents usuaris, obtenir pistes…
També manté un ranking per usuaris segons la puntuació total que hagin aconseguit.
Manté un registre de récords per cada kakuro que s’ha jugat.
Permet generar kakuros manualment o aleatòriament i també carregar-los a
partir d’un fitxer.


![image](https://user-images.githubusercontent.com/19478700/129100177-2fa00982-af72-45b7-8567-783c9520c714.png)
-PLAY: et dirigeix a un nou menú on pots escollir tres opcions: generar un nou kakuro
automàticament o crear-lo manualment, per següentment jugar-lo.
-USER: et dirigeix al panel d’usuari.
-REPOSITORY: et dirigeix al repositori on es pot visualitzar la llista de kakuros guardats.
-RANKING: et dirigeix al ranking, on pots veure les puntuacions dels usuaris que han jugat
prèviament.

![image](https://user-images.githubusercontent.com/19478700/129100222-8f768c13-b8b2-421a-8907-ea59b9607e1d.png)
-Fast Play: Genera un kakuro aleatori i permet a l’usuari jugar-lo
-Continue saved game: Continuar partida guardada anteriorment, si no n’hi ha es mostra
un error.
-Select a Kakuro from repository: Porta a la vista Repositori on es poden escollor kakuros
per jugar.
-Generate new Kakuro to play: Obre la vista del Generador de kakuros on es pot generar
nous kakuros amb restriccions de mida i quantitat de caselles.
-Create a new Kakuro manually: Obre la vista de creació de kakuros on pots “dibuxar” un
kakuro.
-Read Kakuro from file: Obre una pantalla on es demana el nom de l’arxiu on es troba el
kakuro a importar.

![image](https://user-images.githubusercontent.com/19478700/129100260-9ebc93bf-0141-45f8-8798-e34d9692cb45.png)
-Hint: Soluciona un casella a l’atzar.
-Check: Posa de color verd.
-Save: Guarda la partida actual per poder continuar jugant en un altre moment.
-Solve: Soluciona totes les caselles.
-Start Over: Posa totes les caselles en blanc.
-Finish: Finalitzar la partida.
Per canviar el valor d’una casella blanca cal fer hi click amb el ratolí.

![image](https://user-images.githubusercontent.com/19478700/129100290-67f5970f-968e-4855-914a-31f99433d616.png)
La vista Create Kakuro permet crear un kakuro en 3 passos: primer es selecciona el tamany
del Kakuro introduint-lo al textfield de la Fig.1 a la dreta i apretant el boto Next.
En segon lloc permet seleccionar el color de les caselles, per defecte son negres pero si es
prem alguna daquestes es canviara el color a blanc.
En tercer lloc es dona un Kakuro on podem seleccionar el tipus de pista de cada casella
negra.
Per últim permet posar valor a totes les pistes anteriorment seleccionada i jugar el kakuro
que acabem de crear.
![image](https://user-images.githubusercontent.com/19478700/129100527-b4888f6d-5c3c-4104-9e95-ef407a7d41aa.png)


## How does it work?
Algorisme solucionador de kakuros:
S’atribueix per ordre un valor de l’1 al 9 a les caselles blanques (solver) una per una
(next_white_cell) y comprova que es compleixen les restriccions (verificar_casella_run), fent
backtracking en cas contrari.
Quan es troba una solució s’afegeix al solution stack, fent abans una copia
(copia_mat_caselles)
Algorsime generador de kakuros:
Es comença generant un tauler que compleix les especificacions de files, columnes i nº de
caselles blanques que es passen com a paràmetres. També, tenint en compte de no tenir
files ni columnes més llargues de 9 i sense tenir espais d’una casella blanca. A partir d’aqui
s’omplen les caselles blanques amb números intentant
Estructrures de dades utilitzades per represtentar el kakuro:
La nostra represtentació d’un kakuro en Java consta de una array d’arrays (array 2D) que té
com a elements objectes de la classe casella. Cada casella pot ser blanca o negra, si es
blanca té un atribut que es el valor i si es negra en pot tenir fins a dos que son les pistes.


