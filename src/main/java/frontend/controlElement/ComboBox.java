package frontend.controlElement;

import backend.window.main.filter.constant.StatusEnum;
import backend.window.main.form.Region;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import backend.window.main.form.constant.GenderEnum;
import backend.window.main.form.constant.IdentificationKindEnum;
import backend.window.main.form.constant.TypeEnum;

import javax.swing.*;

import static backend.window.main.filter.constant.StatusEnum.WITHOUT_STATUS;
import static backend.window.main.form.constant.EntrepreneurshipEnum.JURIDICAL_PERSON;
import static backend.window.main.form.constant.GenderEnum.M;
import static backend.window.main.form.constant.IdentificationKindEnum.PERSONALLY;
import static backend.window.main.form.constant.TypeEnum.RF_PASSPORT;

public class ComboBox<E> extends JComboBox<E> {

    public static final class Entrepreneurship extends ComboBox<EntrepreneurshipEnum> {

        public Entrepreneurship() {
            super(EntrepreneurshipEnum.values());
            setSelectedItem(JURIDICAL_PERSON);
        }
    }

    public static final class StateOrProvinceName extends ComboBox<String> {

        public StateOrProvinceName() {
            super(Region.getInstance().getSortedListOfRegions());
            setSelectedItem("Москва");
        }
    }

    public static final class IdentificationKind extends ComboBox<IdentificationKindEnum> {

        public IdentificationKind() {
            super(IdentificationKindEnum.values());
            setSelectedItem(PERSONALLY);
        }
    }

    public static final class Gender extends ComboBox<GenderEnum> {

        public Gender() {
            super(GenderEnum.values());
            setSelectedItem(M);
        }
    }

    public static final class Type extends ComboBox<TypeEnum> {

        public Type() {
            super(TypeEnum.values());
            setSelectedItem(RF_PASSPORT);
        }
    }

    public static final class Status extends ComboBox<StatusEnum> {

        public Status() {
            super(StatusEnum.values());
            setSelectedItem(WITHOUT_STATUS);
        }
    }

    private ComboBox(E[] items) {
        super(items);
    }
}
