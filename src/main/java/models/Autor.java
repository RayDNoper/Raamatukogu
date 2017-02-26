package models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by raydnoper on 19/02/17.
 */

@Table("autorid")
public class Autor extends Model {

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getString("nimi"));
        if (getString("synniaeg") != null || getString("surmaaeg") != null) {
            sb.append(" (")
                    .append(getString("synniaeg"))
                    .append("-")
                    .append(getString("surmaaeg"))
                    .append(")");
        }
        return sb.toString();
    }
}
