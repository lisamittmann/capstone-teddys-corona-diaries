/// <reference types="cypress" />

const testId = id => `[data-testid="${id}"]`;
const PROVINCE_CARD = testId('provinceCard')

describe('Display corona details for selected province', () => {
    it('Corona details page loads without details section', () => {
        cy.visit('/coronadetails')
        cy.get(PROVINCE_CARD).should('not.exist')
    })

    it('Corona details should show province card after selection', () => {
        cy.visit('/coronadetails')
        cy.get('select').select('Hamburg')
        cy.get(PROVINCE_CARD).should('be.visible')
    })

})