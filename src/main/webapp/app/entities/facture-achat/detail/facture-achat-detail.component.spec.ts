import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FactureAchatDetailComponent } from './facture-achat-detail.component';

describe('FactureAchat Management Detail Component', () => {
  let comp: FactureAchatDetailComponent;
  let fixture: ComponentFixture<FactureAchatDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FactureAchatDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ factureAchat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FactureAchatDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FactureAchatDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load factureAchat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.factureAchat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
