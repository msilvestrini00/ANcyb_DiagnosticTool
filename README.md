### PROGETTO DI PROGRAMMAZIONE AD OGGETTI A.A. 2021-2022
# ANCYBERNETICS DIAGNOSTIC TOOL 
### _Strumento di diagnostica per dispositivi marini per ricezione dei dati di bordo e valutazione della situazione meteorologica marina_

### INDICE

## INTRODUZIONE

### Contesto
Il progetto è stato proposto come frutto della collaborazione con uno spinoff accademico i cui sviluppatori sono soci.

Quest'ultimo è chiamato ANcybernetics S.r.l. e si occupa dello sviluppo di un robot pesciformi a servizio dell'istruzione e la ricerca.

In un futuro prossimo in cui i dispositivi prodotti possano navigare autonomamente, si è reso necessario un sistema di controllo e diagnostica capace di monitorare 
i parametri principali di ogni robot: il progetto sviluppato si presta come soluzione ideale a questa problematica.

### Descrizione
Il progetto sviluppato è un'applicazione Springboot scritta in linguaggio Java,  che consente di:
- raccogliere dati di diagnostica (temperatura di bordo, coordinate geografiche) da ogni dispositivo STRUMENTI UTILIZZATI in circolazione (tramite un servizio MQTT e l'hardware contenuto in ogni dispositivo)
- ordinare i dati in un database e salvarli in locale tramite un servizio di datalogging
- raccogliere dati di previsione meteorologica dal web relativi alle coordinate dei dispositivi (per mezzo di un'API esterna)
- rendere disponibile all'utente la visualizzazione di questi dati attraverso varie modalità, permettendo di filtrarli o di eseguire statistiche su di essi.

## COMPONENTI E STRUMENTI UTILIZZATI
### REST API
Il servizio di previsione meteorologica marina è stato fornito dall'API Storm Glass. 
Questo servizio si distingue dagli altri simili per la praticità d'uso e l'attendibilità:
- Effettuando la chiamata con un'unica rotta e inserendo le coordinate come parametri, è possibile ricevere 23 tipi di dato differenti sulla situazione meteorologica.
- 
### INTERFACCIA UTENTE

### SERVIZIO MQTT

### HARDWARE DISPOSITIVO
Esso è stato ideato per ospitare della sensoristica, ad esempio un GPS e un termometro.
Abbiamo intenzione di scrivere una routine in micropython nel microcontrollore di bordo che (via WiFi) esso manda i dati ad un servizio remoto: 
successivamente, un client Java prende la posizione del robot e la inserisce in una mappa, indicandone la temperatura.

## ROTTE

## UML

## DIMOSTRAZIONE DI FUNZIONAMENTO

## ECCEZIONI

## TEST

## EVENTUALI SVILUPPI FUTURI
A causa degli scopi prettamente didattici e le tempistiche relativamente ridotte per un progetto reale di tali dimensioni, 
il lavoro ha subito varie semplificazioni funzionali che ne giustificano la reale implementazione.

Di seguito vengono elencate alcune features che avrebbero conferito al lavoro una effettiva utilità in campo:

- Sostituzione del sistema di posizionamento subacqueo con tecnologia USBL al posto di quello GPS (per permettere l'invio dei dati in immersione)
- ....

##AUTORI



