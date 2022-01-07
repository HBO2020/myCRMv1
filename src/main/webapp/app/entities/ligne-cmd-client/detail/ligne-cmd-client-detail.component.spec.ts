import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LigneCmdClientDetailComponent } from './ligne-cmd-client-detail.component';

describe('LigneCmdClient Management Detail Component', () => {
  let comp: LigneCmdClientDetailComponent;
  let fixture: ComponentFixture<LigneCmdClientDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LigneCmdClientDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ligneCmdClient: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LigneCmdClientDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LigneCmdClientDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ligneCmdClient on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ligneCmdClient).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
