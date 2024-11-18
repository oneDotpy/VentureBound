package use_case.change_password;

/**
 * The output data for the change password
 */
public class ChangePasswordOutputData {
    private final boolean useCaseFailed;

    public ChangePasswordOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

}
