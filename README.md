# SportovniZpravy

package websocket

class Worker
Drží seznam klíčů pro čtení zpráv z daných topiců
Pokouší se číst z topicu s intervalem 1s

class WebSocket
Drží mapu klientů pro jednotlivé zápasy a worker pro čtení z topicu
Worker běží v samostatném vlákně
Při otevření nového websocketu přidá clienta pro daný zápas do mapy a přidá workeru klíč topicu, který se má poslouchat
Při zavření websocketu odebere clienta a klíč topicu
Jestliže není žádný client, zastaví worker, který čte z topicu a stopne vlákno. Při přidání clienta nastartuje nové vlákno.

Po přečtení zprávy z topicu rozešle zprávu pouze clientům, kteři jsou připojení k danému zápasu


package HibernateUdalost
obsahuje dotaz pro získání všech událostí(komentářů) k danému sportu
při volání servletu(ServletUdalostiSport  /udalostisport) nefunguje, hibernate data přečte, ovšem typ dat zřejmě neodpovídá třídě Udalost
