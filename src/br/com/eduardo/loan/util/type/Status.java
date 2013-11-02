package br.com.eduardo.loan.util.type;

/**
 * @author Eduardo Matos de Souza <br>
 *         D�gitro - 08/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public enum Status {

    AVAILABLE(0),
    LENDED(1),
    RETURNED(2),
    ARCHIVED(3);

    private Integer id;

    private Status(Integer id) {
        this.id = id;
    }

    public int id() {
        return id;
    }
}
