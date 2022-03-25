package backend.window.main.filter;

import backend.util.Validation;
import backend.window.main.filter.constant.StatusEnum;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FilterData {
    @SerializedName(value = "company")
    private String commonName;
    @SerializedName(value = "inn")
    private String orgINN;
    @SerializedName(value = "lastName")
    private String lastName;
    @SerializedName(value = "snils")
    private String SNILS;
    @SerializedName(value = "createDate")
    private String creationDate;
    @SerializedName(value = "statusId")
    private Integer statusId;

    private boolean nonBlankString(String text) {
        return text != null && !text.isBlank();
    }

    public void setCommonName(String commonName) {
        if (nonBlankString(commonName)) {
            this.commonName = commonName;
        }
    }

    public void setOrgINN(String orgINN) {
        if (nonBlankString(orgINN)) {
            orgINN = Validation.getFormattedOrgINN(orgINN);
            if (Validation.isCorrectOrgINN(orgINN)) {
                this.orgINN = orgINN;
            }
        }
    }

    public void setLastName(String lastName) {
        if (nonBlankString(lastName)) {
            this.lastName = lastName;
        }
    }

    public void setSNILS(String SNILS) {
        if (nonBlankString(SNILS)) {
            SNILS = Validation.getFormattedSNILS(SNILS);
            if (Validation.isCorrectSNILS(SNILS)) {
                this.SNILS = SNILS;
            }
        }
    }

    public void setCreationDate(String creationDate) {
        if (nonBlankString(creationDate)) {
            this.creationDate = Validation.getFormattedDate(creationDate);
        }
    }

    private void setStatusId(@Nullable Integer statusId) {
        this.statusId = statusId;
    }

    public void setStatusId(@NotNull StatusEnum statusEnum) {
        setStatusId(statusEnum.getCode());
    }

    @Override
    public String toString() {
        return "FilterData{" +
                "commonName='" + commonName + '\'' +
                ", orgINN='" + orgINN + '\'' +
                ", lastName='" + lastName + '\'' +
                ", SNILS='" + SNILS + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", statusId=" + statusId +
                '}';
    }
}
