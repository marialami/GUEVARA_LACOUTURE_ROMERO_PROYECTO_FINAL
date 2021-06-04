package logic.persistence.impl;

import logic.entities.Exportable;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class Export implements IExport {

    @Override
    public void export(List<Exportable> exportable, Character separateValue) throws Exception {
        LocalDate now = LocalDate.now();
        String fileName = String.format("export-%s-%s-%s.%s", now.getYear(), now.getMonth(), now.getDayOfMonth(), Exportable.getExtension(separateValue));
        FileOutputStream out = new FileOutputStream(fileName);
        PrintWriter pw = new PrintWriter(out);

        String header = exportable.stream().findAny().get().getHeader();
        pw.println(header);
        System.out.println(header);

        for (Exportable e : exportable) {
            String s = e.toExportValue(separateValue);
            pw.println(s);
            System.out.println(s);
        }

        pw.close();

    }
}
